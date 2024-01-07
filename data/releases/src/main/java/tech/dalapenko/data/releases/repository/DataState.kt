package tech.dalapenko.data.releases.repository

sealed class DataState<T>(val data: T) {

    class Cached<T>(data: T) : DataState<T>(data)
    class Current<T>(data: T) : DataState<T>(data)
}