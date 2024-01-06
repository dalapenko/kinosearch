package tech.dalapenko.premieres.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.dalapenko.network.adapter.onError
import tech.dalapenko.network.adapter.onException
import tech.dalapenko.network.adapter.onSuccess
import tech.dalapenko.premieres.model.entity.Premiere
import tech.dalapenko.premieres.model.repository.PremiereRepository
import tech.dalapenko.premieres.view.State
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PremieresViewModel @Inject constructor(
    private val premiereRepository: PremiereRepository
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<State<List<PremiereListItem>>> = MutableStateFlow(State.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val localDateComparator = Comparator<LocalDate?> { current, other ->
        return@Comparator current?.compareTo(other) ?: -1
    }

    fun fetchContentList(month: String, year: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mutableContentStateFlow.emit(State.Loading)
                val releaseList = premiereRepository.getPremiereMovieList(month, year)

                releaseList
                    .onSuccess {
                        val groupedReleaseList = groupPremiereListByDate(it)
                        mutableContentStateFlow.emit(State.Success(groupedReleaseList))
                    }
                    .onError { _, _ ->
                        mutableContentStateFlow.emit(State.Error)
                    }
                    .onException {
                        mutableContentStateFlow.emit(State.Error)
                    }
            }
        }
    }

    private fun groupPremiereListByDate(
        premiereList: List<Premiere>
    ): List<PremiereListItem> {
        val recyclerItemList = mutableListOf<PremiereListItem>()

        premiereList
            .groupBy { it.premiereDate?.let(::parsePremierDate) }
            .toSortedMap(localDateComparator)
            .map { (date, premieres) ->
                if (premieres.isEmpty()) return@map
                val formattedDate = date?.format(dateTimeFormatter)
                recyclerItemList.add(PremiereListItem.DateItem(formattedDate))
                premieres
                    ?.map(PremiereListItem::PremiereItem)
                    ?.let(recyclerItemList::addAll)
            }

        return recyclerItemList
    }

    private fun parsePremierDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
