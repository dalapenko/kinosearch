package tech.dalapenko.feature.search.viewmodel

sealed class UiState<out T> {

    class Ready<T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data object Error : UiState<Nothing>()

}