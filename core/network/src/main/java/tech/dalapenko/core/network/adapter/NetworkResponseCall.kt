package tech.dalapenko.core.network.adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class NetworkResponseCall<T>(
    private val proxy: Call<T>
): Call<NetworkResponse<T>> {
    override fun clone(): Call<NetworkResponse<T>> {
        return NetworkResponseCall(proxy.clone())
    }

    override fun execute(): Response<NetworkResponse<T>> {
        throw NotImplementedError()
    }

    override fun isExecuted(): Boolean {
        return proxy.isExecuted
    }

    override fun cancel() {
        proxy.cancel()
    }

    override fun isCanceled(): Boolean {
        return proxy.isCanceled
    }

    override fun request(): Request {
        return proxy.request()
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

    override fun enqueue(callback: Callback<NetworkResponse<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResponse = handleResponse(response)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResponse = NetworkResponse.Exception(t)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    private fun <T> handleResponse(
        response: Response<T>
    ) : NetworkResponse<T> {
        return try {
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                NetworkResponse.Success(responseBody)
            } else {
                NetworkResponse.Error(response.code(), response.message())
            }
        } catch (error: HttpException) {
            NetworkResponse.Error(error.code(), error.message())
        } catch (throwable: Throwable) {
            NetworkResponse.Exception(throwable)
        }
    }
}