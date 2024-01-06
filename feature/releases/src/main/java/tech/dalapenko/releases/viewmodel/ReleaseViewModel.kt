package tech.dalapenko.releases.viewmodel

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
import tech.dalapenko.releases.model.State
import tech.dalapenko.releases.model.entity.Release
import tech.dalapenko.releases.model.repository.ReleaseRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val releaseRepository: ReleaseRepository
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<State<List<ReleaseListItem>>> = MutableStateFlow(State.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val localDateComparator = Comparator<LocalDate?> { current, other ->
        return@Comparator current?.compareTo(other) ?: -1
    }

    fun fetchReleasesList(month: String, year: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mutableContentStateFlow.emit(State.Loading)
                val releaseList = releaseRepository.getReleaseMovieList(month, year)

                releaseList
                    .onSuccess {
                        val groupedReleaseList = groupReleaseListByDate(it)
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
                    ?.map(ReleaseListItem::ReleaseItem)
                    ?.let(recyclerItemList::addAll)
            }

        return recyclerItemList
    }

    private fun parsePremierDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
