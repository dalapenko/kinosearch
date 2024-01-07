package tech.dalapenko.data.releases.repository

sealed class DataState<out T>(val data: T) {

    class Cached<T>(data: T) : DataState<T>(data)
    class Current<T>(data: T) : DataState<T>(data)
}