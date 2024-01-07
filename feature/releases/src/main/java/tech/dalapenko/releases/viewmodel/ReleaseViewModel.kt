package tech.dalapenko.releases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.dalapenko.releases.model.DataState
import tech.dalapenko.releases.model.entity.Release
import tech.dalapenko.releases.model.repository.ReleaseRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val releaseRepository: ReleaseRepository
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<UiState<List<ReleaseListItem>>> =
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
                    if (data.data.isEmpty()) {
                        mutableContentStateFlow.emit(UiState.Empty)
                        return@collect
                    }

                    val groupedReleaseList = groupReleaseListByDate(data.data)
                    val uiState = if (data is DataState.Current) {
                        UiState.CurrentData(groupedReleaseList)
                    } else {
                        UiState.CachedData(groupedReleaseList)
                    }
                    mutableContentStateFlow.emit(uiState)
                }
        }
    }

    private fun groupReleaseListByDate(
        premiereList: List<Release>
    ): List<ReleaseListItem> {
        val recyclerItemList = mutableListOf<ReleaseListItem>()

        premiereList
            .groupBy { it.releaseDate?.let(::parsePremierDate) }
            .toSortedMap(localDateComparator)
            .map { (date, releases) ->
                if (releases.isEmpty()) return@map
                val formattedDate = date?.format(dateTimeFormatter)
                recyclerItemList.add(ReleaseListItem.DateItem(formattedDate))
                releases
                    ?.sortedBy(Release::id)
                    ?.map(ReleaseListItem::ReleaseItem)
                    ?.let(recyclerItemList::addAll)
            }

        return recyclerItemList
    }

    private fun parsePremierDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
