package net.hyperia

import net.hyperia.retrofit.HyperiaApiFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.time.LocalDate
import java.time.OffsetTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class HyperiaClientTest {
    private val baseUri = System.getProperty("baseUri")
    private val testApiKey = System.getProperty("testApiKey")

    private val api = HyperiaApiFactory().v1(baseUri)
    private val client = HyperiaClient(api, testApiKey)
    private var workspaceId: String = ""
    private var readonlyWorkspaceId: String? = null
    private var currentStream: StreamInfo? = null
    private var ingestId: String? = null
    private var docId: String? = null
    private val pause = CountDownLatch(1)

    @BeforeAll
    fun beforeAll() {
        workspaceId = client.workspaceCreate("Test${System.currentTimeMillis()}").result.workspaceId
        println(workspaceId)
    }

    @AfterAll
    fun afterAll() {
        val status = client.workspaceDelete(workspaceId)
        assertEquals("ok", status.status)
    }

    @Test
    @Order(1)
    fun testFileIngest() {
        val fileUrl = this.javaClass.getResource("test_ingest.mp3")
        assertNotNull(fileUrl)
        val file = File(fileUrl!!.toURI())
        val ingestId = client.fileIngest(workspaceId, IngestPayload("test${System.currentTimeMillis()}",
            LocalDate.now(), OffsetTime.now()), file)
        assertNotNull(ingestId.guid)
        this.ingestId = ingestId.guid
        println(this.ingestId)
    }
    @Test
    @Order(2)
    fun testWorkspaceList() {
        val workspaces = client.workspaceList()
        assertEquals("ok", workspaces.status)
        println(workspaces)
        readonlyWorkspaceId = workspaces.results.filter { it.numDocs > 0 }.firstOrNull()?.workspaceId
    }

    @Test
    @Order(3)
    fun testPublicWorkspaceList() {
        val workspaces = client.publicWorkspaceList();
        assertEquals("ok", workspaces.status)
        println(workspaces)
    }

    @Test
    @Order(4)
    fun testStreamCreate() {
        currentStream = client.streamCreate().result
        assertNotNull(currentStream)
    }

    @Test
    @Order(5)
    fun testStreamList() {
        val streams = client.streamList()
        assertEquals("ok", streams.status)
        assertTrue(streams.results.isNotEmpty())
        println(streams.results)
    }

    @Test
    @Order(6)
    fun testStreamExists() {
        val objExist = client.streamExist(currentStream!!.streamId)
        assertTrue(objExist.exists)
        assertEquals("ok", objExist.status)
        assertTrue(objExist.exists)
    }

    @Test
    @Order(7)
    fun testMeetingList() {
        val meetings = client.meetingList()
        assertEquals("ok", meetings.status)
        println(meetings)
    }

    @Test
    @Order(8)
    fun testDocList() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docList(readonlyWorkspaceId!!)
        assertTrue(res.results.isNotEmpty())
        docId = res.results.first().id
        println(res)
    }

    @Test
    @Order(9)
    fun testDocRankedTopic() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docRankedTopic(readonlyWorkspaceId!!)
        assertEquals("ok", res.status)
        assertTrue(res.topics.isNotEmpty())
        println(res)
    }

    @Test
    @Order(10)
    fun testDocFilteredRankedTopic() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docRankedTopic(readonlyWorkspaceId!!, DocSearchFilter(topicList = listOf("java")))
        assertEquals("ok", res.status)
        println(res)
    }

    @Test
    @Order(11)
    fun testDocRankedTag() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docRankedTag(readonlyWorkspaceId!!)
        assertEquals("ok", res.status)
        println(res)
    }

    @Test
    @Order(12)
    fun testDocFilteredRankedTag() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docRankedTag(readonlyWorkspaceId!!, DocSearchFilter(titlePhraseMatchList = listOf("Opinion")))
        assertEquals("ok", res.status)
        println(res)
    }

    @Test
    @Order(13)
    fun testDocRankedSpeaker() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docRankedSpeaker(readonlyWorkspaceId!!)
        assertEquals("ok", res.status)
        println(res)
    }

    @Test
    @Order(14)
    fun testDocFilteredRankedSpeaker() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docRankedSpeaker(readonlyWorkspaceId!!,
            DocSearchFilter(descriptionPhraseMatchList = listOf("discusses"))
        )
        assertEquals("ok", res.status)
        println(res)
    }

    @Test
    @Order(15)
    fun testDocSearchTranscript() {
        if (readonlyWorkspaceId == null) {
            println("Readonly workspace with docs missing, skip")
            return
        }
        val res = client.docSearchTranscript(readonlyWorkspaceId!!, TranscriptSearchFilter(topicList = listOf("java")))
        println(res)
    }

    @Test
    @Order(16)
    fun testDocExists() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docExists(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        assertTrue(res.exists)
        println(res)
    }

    @Test
    @Order(17)
    fun testDocInfo() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docInfo(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        println(res)
    }

    @Test
    @Order(18)
    fun testDocTranscript() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docTranscript(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        assertTrue(res.transcript.isNotEmpty())
        println(res)
    }

    @Test
    @Order(19)
    fun testDocSummary() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docSummary(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        assertTrue(res.summary.isNotEmpty())
        println(res)
    }

    @Test
    @Order(20)
    fun testDocMonologues() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docMonologues(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        assertTrue(res.monologues.isEmpty())
        println(res)
    }

    @Test
    @Order(21)
    fun testDocTopics() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docTopics(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        assertTrue(res.topics.isNotEmpty())
        println(res)
    }

    @Test
    @Order(22)
    fun testDocTags() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docTags(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        assertTrue(res.tags.isEmpty())
        println(res)
    }

    @Test
    @Order(23)
    fun testDocScreens() {
        if (docId == null) {
            println("Readonly doc id missing, skip")
            return
        }
        val res = client.docScreens(readonlyWorkspaceId!!, docId!!)
        assertEquals("ok", res.status)
        println(res)
    }
}