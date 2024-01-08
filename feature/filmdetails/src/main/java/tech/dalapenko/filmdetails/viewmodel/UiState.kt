package tech.dalapenko.filmdetails.viewmodel

sealed class UiState<out T> {

    class Success<T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data object Error : UiState<Nothing>()
}