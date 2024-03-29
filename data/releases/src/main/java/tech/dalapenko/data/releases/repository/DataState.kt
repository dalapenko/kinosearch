package tech.dalapenko.data.releases.repository

sealed interface DataState<out T> {
    class Cached<T>(val data: T) : DataState<T>
    class Current<T>(val data: T) : DataState<T>
    data object Loading : DataState<Nothing>
    data object FetchError : DataState<Nothing>
}