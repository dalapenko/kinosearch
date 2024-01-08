package tech.dalapenko.feature.filmdetails.model

sealed class UiState<out T> {

    class Success<T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data object Error : UiState<Nothing>()
}