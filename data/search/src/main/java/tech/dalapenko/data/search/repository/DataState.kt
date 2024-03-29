package tech.dalapenko.data.search.repository

sealed interface DataState<out T> {

    class Ready<T>(val data: T) : DataState<T>
    data object Loading : DataState<Nothing>
    data object FetchError : DataState<Nothing>
}