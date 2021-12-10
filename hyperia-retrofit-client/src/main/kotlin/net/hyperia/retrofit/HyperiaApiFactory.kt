package net.hyperia.retrofit

import com.fasterxml.jackson.databind.ObjectMapper
import net.hyperia.defaultObjectMapper
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

fun defaultHttpClient() : OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY;

    return OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .pingInterval(2, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
        .addInterceptor(logging)
        .build()
}

class HyperiaApiFactory {
    @JvmOverloads
    fun v1(baseUrl: String,
           httpClient: OkHttpClient = defaultHttpClient(),
           objectMapper: ObjectMapper = defaultObjectMapper()
    ): HyperiaApiV1 {
        val json = JacksonConverterFactory.create(objectMapper)
        val jsonBinary = JsonBinaryConverterFactory(objectMapper)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(FileConverterFactory())
            .addConverterFactory(JsonConverterFactory(jsonBinary, json))
            .client(httpClient)
            .build()
        return retrofit.create(HyperiaApiV1::class.java)
    }
}