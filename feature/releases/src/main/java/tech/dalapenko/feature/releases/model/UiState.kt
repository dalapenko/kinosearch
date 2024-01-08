package tech.dalapenko.feature.releases.model

sealed class UiState<out T> {

    class CurrentDataReady<T>(val data: T) : UiState<T>()

    class CachedDataReady<T>(val data: T): UiState<T>()
    data object Loading : UiState<Nothing>()
    data object Error : UiState<Nothing>()

}