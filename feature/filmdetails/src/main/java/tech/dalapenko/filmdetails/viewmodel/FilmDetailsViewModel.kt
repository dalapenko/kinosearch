package tech.dalapenko.filmdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.dalapenko.filmdetails.model.entity.Film
import tech.dalapenko.filmdetails.model.repository.FilmDetailsRepository
import tech.dalapenko.filmdetails.view.State
import tech.dalapenko.network.adapter.onError
import tech.dalapenko.network.adapter.onException
import tech.dalapenko.network.adapter.onSuccess
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val filmDetailsRepository: FilmDetailsRepository
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<State<Film>> =
        MutableStateFlow(State.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()

    fun fetchFilmData(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mutableContentStateFlow.emit(State.Loading)
                val releaseList = filmDetailsRepository.getFilmDetails(id)

                releaseList
                    .onSuccess {
                        mutableContentStateFlow.emit(State.Success(it))
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
}
