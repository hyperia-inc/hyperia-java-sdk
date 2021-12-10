package net.hyperia

import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

internal class DataBindingTest {
    private val objectMapper = defaultObjectMapper()

    @Test
    fun dateFormatTest() {
        val format = DateTimeFormatter.ISO_DATE
        val gte = LocalDate.now().minusDays(2)
        val lte = LocalDate.now().minusDays(1)
        val dr = DateRange(timeZone = "+03:00", gte = gte, lte = lte)

        val drStr = objectMapper.writeValueAsString(dr)
        assertEquals(
            "{\"time_zone\":\"+03:00\",\"gte\":\"${gte.format(format)}\",\"lte\":\"${lte.format(format)}\"}",
            drStr)
    }

    @Test
    fun timeFormatTest() {
        val format = DateTimeFormatter.ofPattern("HH:mm:ss")
        val gte = LocalTime.now().minusHours(4)
        val lte = LocalTime.now().minusHours(3)
        val tr = TimeRange(timeZone = "+03:00", gte = gte, lte = lte)

        val trStr = objectMapper.writeValueAsString(tr)
        assertEquals(
            "{\"time_zone\":\"+03:00\",\"gte\":\"${gte.format(format)}\",\"lte\":\"${lte.format(format)}\"}",
            trStr)
    }

    @Test
    fun offsetTimeFormatTest() {
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm:ssZ")
        val date = LocalDate.now()
        val time = OffsetTime.now()
        val pStr = objectMapper.writeValueAsString(IngestPayload("test", date, time))
        assertEquals("{\"title\":\"test\",\"date\":" +
                "\"${date.format(DateTimeFormatter.ISO_DATE)}\",\"time\":\"${time.format(timeFormat)}\"}", pStr)
    }

    @Test
    fun deserializationNamesTest() {
        val objStr = "{\"text\":\"Screen data text\", \"minX\": 123, \"minY\": 123, \"maxX\": 123, \"maxY\": 123}"
        val res = objectMapper.readValue<ScreenOcr>(objStr)
        assertEquals(123, res.maxX)
        assertEquals(123, res.maxY)
        assertEquals(123, res.minX)
        assertEquals(123, res.minY)
        assertEquals("Screen data text", res.text)
    }
}