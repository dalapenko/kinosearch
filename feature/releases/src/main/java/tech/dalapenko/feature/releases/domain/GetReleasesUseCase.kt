package tech.dalapenko.feature.releases.domain

import kotlinx.coroutines.flow.flow
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.data.releases.repository.DataState
import tech.dalapenko.data.releases.repository.ReleaseRepository
import tech.dalapenko.feature.releases.model.DateItem
import tech.dalapenko.feature.releases.model.ReleaseItem
import tech.dalapenko.feature.releases.model.UiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetReleasesUseCase @Inject constructor(
    private val releasesRepository: ReleaseRepository
) {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val localDateComparator = Comparator<LocalDate?> { current, other ->
        return@Comparator current?.compareTo(other) ?: -1
    }

    suspend operator fun invoke(
        month: String, year: Int
    ) = flow {
        emit(UiState.Loading)
        releasesRepository.getReleasesList(month, year)
            .collect { data ->
                when (data) {
                    is DataState.Loading -> {
                        emit(UiState.Loading)
                    }
                    is DataState.FetchError -> {
                        emit(UiState.Error)
                    }
                    is DataState.Cached -> {
                        val groupedReleaseList = groupReleaseListByDate(data.data)
                        emit(UiState.CachedDataReady(groupedReleaseList))
                    }
                    is DataState.Current -> {
                        val groupedReleaseList = groupReleaseListByDate(data.data)
                        emit(UiState.CurrentDataReady(groupedReleaseList))
                    }
                }
            }
    }

    private fun groupReleaseListByDate(
        premiereList: List<Release>
    ): List<SectionRecyclerAdapter.Item> {
        val recyclerItemList = mutableListOf<SectionRecyclerAdapter.Item>()

        premiereList
            .groupBy { it.releaseDate?.let(::parsePremierDate) }
            .toSortedMap(localDateComparator)
            .map { (date, releases) ->
                if (releases.isEmpty()) return@map
                val formattedDate = date?.format(dateTimeFormatter)
                recyclerItemList.add(DateItem(formattedDate))
                releases
                    ?.sortedBy(Release::id)
                    ?.map(::ReleaseItem)
                    ?.let(recyclerItemList::addAll)
            }

        return recyclerItemList
    }

    private fun parsePremierDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}