package net.hyperia.retrofit

import net.hyperia.HyperiaClientException
import retrofit2.Call


fun <T> handleCall(call: Call<T>): T {
    val response = try {
        call.execute()
    } catch (ex: Exception) {
        throw HyperiaClientException("Request execution error", ex)
    }
    if (!response.isSuccessful) {
        val error = response.errorBody()
        val data = error?.charStream()?.use {
            it.readText();
        }
        throw HyperiaClientException("Response error: ${response.code()} $data ${response.headers()}")
    }
    return response.body() ?: throw HyperiaClientException("Unexpectedly empty response")
}