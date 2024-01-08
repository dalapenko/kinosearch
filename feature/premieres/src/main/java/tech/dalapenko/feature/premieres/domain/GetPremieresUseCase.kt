package tech.dalapenko.feature.premieres.domain

import kotlinx.coroutines.flow.flow
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.data.premieres.repository.DataState
import tech.dalapenko.data.premieres.repository.PremieresRepository
import tech.dalapenko.feature.premieres.model.DateItem
import tech.dalapenko.feature.premieres.model.PremiereItem
import tech.dalapenko.feature.premieres.model.UiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetPremieresUseCase @Inject constructor(
    private val premieresRepository: PremieresRepository
) {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val localDateComparator = Comparator<LocalDate?> { current, other ->
        return@Comparator current?.compareTo(other) ?: -1
    }

    suspend operator fun invoke(
        month: String, year: Int
    ) = flow {
        emit(UiState.Loading)
        premieresRepository.getPremieresList(month, year)
            .collect { data ->
                when (data) {
                    is DataState.Loading -> {
                        emit(UiState.Loading)
                    }
                    is DataState.FetchError -> {
                        emit(UiState.Error)
                    }
                    is DataState.Cached -> {
                        val groupedReleaseList = groupPremiereListByDate(data.data)
                        emit(UiState.CachedDataReady(groupedReleaseList))
                    }
                    is DataState.Current -> {
                        val groupedReleaseList = groupPremiereListByDate(data.data)
                        emit(UiState.CurrentDataReady(groupedReleaseList))
                    }
                }
            }
    }

    private fun groupPremiereListByDate(
        premiereList: List<Premiere>
    ): List<SectionRecyclerAdapter.Item> {
        val recyclerItemList = mutableListOf<SectionRecyclerAdapter.Item>()

        premiereList
            .groupBy { it.premiereDate?.let(::parsePremierDate) }
            .toSortedMap(localDateComparator)
            .map { (date, premieres) ->
                if (premieres.isEmpty()) return@map
                val formattedDate = date?.format(dateTimeFormatter)
                recyclerItemList.add(DateItem(formattedDate))
                premieres
                    ?.sortedBy(Premiere::id)
                    ?.map(::PremiereItem)
                    ?.let(recyclerItemList::addAll)
            }

        return recyclerItemList
    }

    private fun parsePremierDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}