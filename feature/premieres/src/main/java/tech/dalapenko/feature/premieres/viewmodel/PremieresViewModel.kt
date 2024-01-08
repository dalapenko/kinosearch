package tech.dalapenko.feature.premieres.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.data.premieres.repository.DataState
import tech.dalapenko.data.premieres.repository.PremieresRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PremieresViewModel @Inject constructor(
    private val premieresRepository: PremieresRepository
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<UiState<List<SectionRecyclerAdapter.Item>>> =
        MutableStateFlow(UiState.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val localDateComparator = Comparator<LocalDate?> { current, other ->
        return@Comparator current?.compareTo(other) ?: -1
    }

    fun fetchContentList(month: String, year: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mutableContentStateFlow.emit(UiState.Loading)
                premieresRepository.getPremieresList(month, year)
                    .collect { data ->
                        when (data) {
                            is DataState.Loading -> {
                                mutableContentStateFlow.emit(UiState.Loading)
                            }
                            is DataState.FetchError -> {
                                mutableContentStateFlow.emit(UiState.Error)
                            }
                            is DataState.Cached -> {
                                val groupedReleaseList = groupPremiereListByDate(data.data)
                                mutableContentStateFlow.emit(
                                    UiState.CachedDataReady(
                                        groupedReleaseList
                                    )
                                )
                            }
                            is DataState.Current -> {
                                val groupedReleaseList = groupPremiereListByDate(data.data)
                                mutableContentStateFlow.emit(
                                    UiState.CurrentDataReady(
                                        groupedReleaseList
                                    )
                                )
                            }
                        }
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
