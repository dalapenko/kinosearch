package tech.dalapenko.feature.releases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.data.releases.repository.DataState
import tech.dalapenko.data.releases.repository.ReleaseRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val releaseRepository: ReleaseRepository
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<UiState<List<SectionRecyclerAdapter.Item>>> =
        MutableStateFlow(UiState.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val localDateComparator = Comparator<LocalDate?> { current, other ->
        return@Comparator current?.compareTo(other) ?: -1
    }

    fun fetchReleasesList(month: String, year: Int) {
        viewModelScope.launch {
            mutableContentStateFlow.emit(UiState.Loading)
            releaseRepository.getReleasesList(month, year)
                .collect { data ->
                    when (data) {
                        is DataState.Loading -> {
                            mutableContentStateFlow.emit(UiState.Loading)
                        }
                        is DataState.FetchError -> {
                            mutableContentStateFlow.emit(UiState.Error)
                        }
                        is DataState.Cached -> {
                            val groupedReleaseList = groupReleaseListByDate(data.data)
                            mutableContentStateFlow.emit(UiState.CachedDataReady(groupedReleaseList))
                        }
                        is DataState.Current -> {
                            val groupedReleaseList = groupReleaseListByDate(data.data)
                            mutableContentStateFlow.emit(UiState.CurrentDataReady(groupedReleaseList))
                        }
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
