package net.hyperia.retrofit

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Files

class FileConverter : Converter<File, RequestBody> {
    override fun convert(file: File): RequestBody {
        val contentType = Files.probeContentType(file.toPath())
        return RequestBody.create(MediaType.parse(contentType), file)
    }
}

class FileConverterFactory: Converter.Factory() {
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (type != File::class.java)  {
            return null
        }
        return FileConverter()
    }
}