package tech.dalapenko.releases.model

sealed class State<out T> {

    class Success<T>(val data: T) : State<T>()
    data object Loading : State<Nothing>()
    data object Error : State<Nothing>()

}