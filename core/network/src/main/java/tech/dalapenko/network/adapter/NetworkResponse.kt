package tech.dalapenko.network.adapter

sealed interface NetworkResponse<out T> {

    class Success<T>(val data: T): NetworkResponse<T>

    class Error(val code: Int, val message: String?): NetworkResponse<Nothing>

    class Exception(val throwable: Throwable): NetworkResponse<Nothing>
}

suspend fun <T> NetworkResponse<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Success) {
        executable.invoke(data)
    }
}

suspend fun <T> NetworkResponse<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Error) {
        executable.invoke(code, message)
    }
}

suspend fun <T> NetworkResponse<T>.onException(
    executable: suspend (throwable: Throwable) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Exception) {
        executable.invoke(throwable)
    }
}

fun <IN, OUT>NetworkResponse<IN>.mapOnSuccess(
    transformation: (IN) -> OUT
): NetworkResponse<OUT>  {
    return when (this) {
        is NetworkResponse.Success -> {
            NetworkResponse.Success(transformation.invoke(data))
        }
        is NetworkResponse.Error -> {
            NetworkResponse.Error(code, message)
        }
        is NetworkResponse.Exception -> {
            NetworkResponse.Exception(throwable)
        }
    }
}
