package tech.dalapenko.filmdetails.view

sealed class State<out T> {

    class Success<T>(val data: T) : State<T>()
    data object Loading : State<Nothing>()
    data object Error : State<Nothing>()
}