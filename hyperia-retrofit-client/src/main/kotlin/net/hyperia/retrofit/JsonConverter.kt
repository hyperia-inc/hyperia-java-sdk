package net.hyperia.retrofit

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.Part
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Files

class JsonBinaryConverter(private val objectMapper: ObjectMapper) : Converter<Any, RequestBody> {
    override fun convert(value: Any): RequestBody {
        val tempFile = Files.createTempFile("ingestPayload", "").toFile()
        tempFile.deleteOnExit()
        objectMapper.writeValue(tempFile, value)
        return RequestBody.create(MediaType.parse("application/json"), tempFile)
    }
}

class JsonBinaryConverterFactory(private val objectMapper: ObjectMapper): Converter.Factory() {
    override fun requestBodyConverter(type: Type,
                                      parameterAnnotations: Array<out Annotation>,
                                      methodAnnotations: Array<out Annotation>,
                                      retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (methodAnnotations.any { it is Multipart } and parameterAnnotations.any {
                it is Part && it.value!=""
            }) {
            return JsonBinaryConverter(objectMapper)
        }
        return null
    }
}

class JsonConverterFactory(
    private val binaryDelegate: JsonBinaryConverterFactory,
    private val jsonDelegate: JacksonConverterFactory
): Converter.Factory() {
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (type == File::class.java)  {
            return null
        }
        return binaryDelegate.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit) ?:
        return jsonDelegate.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return jsonDelegate.responseBodyConverter(type, annotations, retrofit)
    }
}