package tech.dalapenko.feature.releases.viewmodel

sealed class UiState<out T> {

    class CurrentData<T>(val data: T) : UiState<T>()

    class CachedData<T>(val data: T): UiState<T>()
    data object Loading : UiState<Nothing>()
    data object Empty : UiState<Nothing>()

}