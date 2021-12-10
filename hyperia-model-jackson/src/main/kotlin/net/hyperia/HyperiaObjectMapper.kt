package net.hyperia

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.LocalTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

class HyperiaOffsetTimeSerializer: OffsetTimeSerializer(
    INSTANCE, false,
    DateTimeFormatter.ofPattern("HH:mm:ssZ")
    )


fun defaultObjectMapper(): ObjectMapper {
    return ObjectMapper()
        .registerModule(
            JavaTimeModule()
                .addSerializer(LocalTime::class.java, LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .addSerializer(OffsetTime::class.java, HyperiaOffsetTimeSerializer())
        )
        .registerModule(Jdk8Module())
        .registerModule(KotlinModule.Builder().build())
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

        .addMixIn(ScreenOcr::class.java, PointNamesExclusion::class.java)
        .addMixIn(Summary::class.java, TimeNamesExclusion::class.java)
        .addMixIn(Mention::class.java, TimeNamesExclusion::class.java)
        .addMixIn(DocInfo::class.java, TimeNamesExclusion::class.java)
        .addMixIn(DocTranscript::class.java, TimeNamesExclusion::class.java)
        .addMixIn(DocSummary::class.java, TimeNamesExclusion::class.java)
        .addMixIn(DocMonologue::class.java, TimeNamesExclusion::class.java)
        .addMixIn(DocScreen::class.java, TimeNamesExclusion::class.java)
        .addMixIn(Utterance::class.java, TimeNamesExclusion::class.java)
        .addMixIn(Participant::class.java, ParticipantMixIn::class.java)

        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
}