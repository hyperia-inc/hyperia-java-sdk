package net.hyperia

import java.net.URL
import java.time.*

data class Result<T>(val result: T, val status: String)
data class Results<T>(val results: List<T>, val status: String)

data class Status(val status: String)

data class Workspace(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceDescription: String?,
    val numDocs: Int,
    val creationDatetime: String
)

data class WorkspaceId(
    val workspaceId: String
)

data class WorkspaceData(val workspaceName: String)

data class ObjectExist(val status: String, val exists: Boolean)
data class Speaker(val id: String, val name: String)
data class Tag(val text: String, val count: Int)
data class Topic(val text: String, val count: Int)
data class Summary(val startTime: Int, val endTime: Int, val speakers: List<Speaker>, val text: String)
data class ScreenOcr(val text: String, val minX: Int, val minY: Int, val maxX: Int, val maxY: Int)
data class Mention(val startTime: Int, val endTime: Int, val speaker: Speaker, val transcript: String)

data class Participant(val email: String?,
                       val id: String,
                       val longestMonologue: Float,
                       val name: String,
                       val totalTalkTime: Float,
                       val totalWordCount: Int
)

data class Doc(val id: String,
               val participants: List<Participant>,
               val labels: List<Label>,
               val tags: List<String>,
               val title: String,
               val description: String,
               val ingestDate: String,
               val date: OffsetDateTime
)
data class DocsResult(val results: List<Doc>)
data class DocInfo(val title: String, val description: String, val duration: Int, val speakers: List<Speaker>)
data class DocTranscript(val startTime: Int, val endTime: Int, val speaker: Speaker, val transcript: String)
data class DocSummary(val startTime: Int, val endTime: Int, val speakers: List<Speaker>, val summaries: List<Summary>)
data class DocMonologue(val startTime: Int, val endTime: Int, val speakers: List<Speaker>, val title: String, val transcript: List<DocTranscript>)
data class DocScreenOccurrence(val lines: List<ScreenOcr>)
data class DocScreen(val startTime: Int, val endTime: Int, val title: String, val ocr: DocScreenOccurrence, val mentions: List<Mention>)
data class DocTopic(val text: String, val type: String, val count: Int, val mentions: List<Mention>)
data class DocTag(val name: String, val id: String, val count: Int, val mentions: List<Mention>)

data class Label(val labelId: String, val labelName: String)
data class TranscriptAbout(val description: String, val labels: List<Label>)
data class Utterance(val startTime: Int, val endTime: Int, val speaker: Speaker, val transcript: String)
data class Transcript(val about: TranscriptAbout, val duration: Int, val speakers: List<Speaker>?, val utterances: List<Utterance>)

data class DocInfoResult(val info: DocInfo, val status: String)
data class DocSummaryResult(val summary: List<DocSummary>, val status: String)
data class DocMonologuesResult(val monologues: List<DocMonologue>, val status: String)
data class DocScreensResult(val screens: List<DocScreen>, val status: String)
data class DocTopicsResult(val topics: List<DocTopic>, val status: String)
data class DocTagsResult(val tags: List<DocTag>, val status: String)

data class DocTranscriptResult(val transcript: List<DocTranscript>, val status: String)
data class SpeakersResult(val speakers: List<Speaker>, val status: String)
data class TagsResult(val tags: List<Tag>, val status: String)
data class TopicsResult(val topics: List<Topic>, val status: String)
data class TranscriptsResult(val results: List<Transcript>)

data class StreamId(val streamId: String)
data class StreamInfo(val streamId: String, val audioSocket: String, val eventSocket: String)
data class IngestPayload(val title: String, val date: LocalDate, val time: OffsetTime)
data class IngestId(val guid: String)
data class IngestUrlData(val title: String, val url: URL)

data class MeetingJoinData @JvmOverloads constructor(val meetingLink: String, val password: String?=null)

data class MeetingData(val meetingId: String,
                       val meetingLink: String,
                       val meetingType: String,
                       val meetingPassword: String? = null)
data class NotetakerStatus(val state: Int, val description: String)

data class Meeting(val meetingId: String,
                   val status: String,
                   val startTimeUtc: String,
                   val durationMinutes: Int,
                   val hardStop: Boolean,
                   val meetingData: MeetingData,
                   val notetakerName: String,
                   val notetakerStatus: NotetakerStatus
)

data class DateRange(val timeZone: String, val gte: LocalDate, val lte: LocalDate)
data class TimeRange(val timeZone: String, val gte: LocalTime, val lte: LocalTime)

data class DocSearchFilter(
    val titlePhraseMatchList: List<String> ?= null,
    val descriptionPhraseMatchList: List<String> ?= null,
    val speakerList: List<String> ?= null,
    val topicList: List<String> ?= null,
    val dateRange: DateRange ?= null,
    val timeRange: TimeRange ?= null,
    val labelIdList: List<String> ?= null,
    val tagIdList: List<String> ?= null
)

data class TranscriptSearchFilter(
    val utterancePhraseMatchList: List<String> ?= null,
    val speakerList: List<String> ?= null,
    val topicList: List<String> ?= null,
    val dateRange: DateRange ?= null,
    val timeRange: TimeRange ?= null,
    val tagIdList: List<String> ?= null,
    val docIdList: List<String> ?= null
)

