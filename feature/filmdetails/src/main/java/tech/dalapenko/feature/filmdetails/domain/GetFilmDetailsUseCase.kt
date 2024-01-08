package tech.dalapenko.feature.filmdetails.domain

import kotlinx.coroutines.flow.flow
import tech.dalapenko.data.filmdetails.repository.DataState
import tech.dalapenko.data.filmdetails.repository.FilmDetailsRepository
import tech.dalapenko.feature.filmdetails.model.UiState
import javax.inject.Inject

class GetFilmDetailsUseCase @Inject constructor(
    private val filmDetailsRepository: FilmDetailsRepository
) {

    suspend operator fun invoke(
        id: Int
    ) = flow {
        emit(UiState.Loading)
        filmDetailsRepository.getFilmDetails(id)
            .collect { data ->
                val uiState = when(data) {
                    is DataState.Current -> UiState.Success(data.data)
                    is DataState.Loading -> UiState.Loading
                    is DataState.FetchError -> UiState.Error
                }

                emit(uiState)
            }
    }
}