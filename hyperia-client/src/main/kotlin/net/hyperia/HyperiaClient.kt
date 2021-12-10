package net.hyperia

import net.hyperia.retrofit.HyperiaApiV1
import net.hyperia.retrofit.handleCall
import java.io.File

class HyperiaClient @JvmOverloads constructor(
    private val api: HyperiaApiV1,
    private val apiKey: String?=null) {

    /**
     * Returns the wrapped list of workspaces
     */
    @JvmOverloads
    fun workspaceList(apiKey: String? = null): Results<Workspace> {
        return handleCall(api.listWorkspaces(currentApiKey(apiKey)))
    }

    /**
     * Returns the wrapped list of public workspaces
     */
    @JvmOverloads
    fun publicWorkspaceList(apiKey: String? = null): Results<Workspace> {
        return handleCall(api.listPublicWorkspaces(currentApiKey(apiKey)))
    }

    /**
     * Deletes the workspace
     */
    @JvmOverloads
    fun workspaceDelete(workspaceId: String, apiKey: String? = null): Status {
        return handleCall(api.deleteWorkspace(currentApiKey(apiKey), workspaceId))
    }

    /**
     * Creates a new workspace
     */
    @JvmOverloads
    fun workspaceCreate(name: String, apiKey: String? = null): Result<WorkspaceId> {
        return handleCall(api.createWorkspace(currentApiKey(apiKey), WorkspaceData(name)))
    }

    /**
     * Returns the list of doc ids in the workspace
     */
    //TODO doc list type
    @JvmOverloads
    fun docList(workspaceId: String, apiKey: String? = null): DocsResult {
        return handleCall(api.listDoc(currentApiKey(apiKey), workspaceId))
    }

    /**
     * Retrieves filtered top topics in the workspace conversations
     */
    @JvmOverloads
    fun docRankedTopic(workspaceId: String, filter: DocSearchFilter, apiKey: String? = null): TopicsResult {
        return handleCall(api.getTopRankedTopics(currentApiKey(apiKey), workspaceId, filter))
    }

    /**
     * Retrieves top topics in the workspace conversations
     */
    @JvmOverloads
    fun docRankedTopic(workspaceId: String, apiKey: String? = null): TopicsResult {
        return handleCall(api.getTopRankedTopics(currentApiKey(apiKey), workspaceId))
    }

    /**
     * Retrieves top tags in the workspace conversations
     */
    @JvmOverloads
    fun docRankedTag(workspaceId: String, apiKey: String? = null): TagsResult {
        return handleCall(api.getTopRankedTags(currentApiKey(apiKey), workspaceId))
    }

    /**
     * Retrieves filtered top tags in the workspace conversations
     */
    @JvmOverloads
    fun docRankedTag(workspaceId: String, filter: DocSearchFilter, apiKey: String? = null): TagsResult {
        return handleCall(api.getTopRankedTags(currentApiKey(apiKey), workspaceId, filter))
    }

    /**
     * Retrieves the top speakers in the workspace conversations
     */
    @JvmOverloads
    fun docRankedSpeaker(workspaceId: String, apiKey: String? = null): SpeakersResult {
        return handleCall(api.getTopRankedSpeakers(currentApiKey(apiKey), workspaceId))
    }

    /**
     * Retrieves the filtered top speakers in the workspace conversations
     */
    @JvmOverloads
    fun docRankedSpeaker(workspaceId: String, filter: DocSearchFilter, apiKey: String? = null): SpeakersResult {
        return handleCall(api.getTopRankedSpeakers(currentApiKey(apiKey), workspaceId, filter))
    }

    /**
     * Search transcripts of the workspaces conversations
     */
    @JvmOverloads
    fun docSearchTranscript(workspaceId: String, filter: TranscriptSearchFilter, apiKey: String? = null): TranscriptsResult {
        return handleCall(api.searchTranscript(currentApiKey(apiKey), workspaceId, filter))
    }

    /**
     * Deletes the doc in the workspace
     */
    @JvmOverloads
    fun docDelete(workspaceId: String, docId: String, apiKey: String? = null): Status {
        return handleCall(api.deleteDoc(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Check whether the doc exists in the workspace
     */
    @JvmOverloads
    fun docExists(workspaceId: String, docId: String, apiKey: String? = null): ObjectExist {
        return handleCall(api.existDoc(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc info
     */
    @JvmOverloads
    fun docInfo(workspaceId: String, docId: String, apiKey: String? = null): DocInfoResult {
        return handleCall(api.getDocInfo(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc transcript
     */
    @JvmOverloads
    fun docTranscript(workspaceId: String, docId: String, apiKey: String? = null): DocTranscriptResult {
        return handleCall(api.getDocTranscript(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc summary
     */
    @JvmOverloads
    fun docSummary(workspaceId: String, docId: String, apiKey: String? = null): DocSummaryResult {
        return handleCall(api.getDocSummary(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc monologues
     */
    @JvmOverloads
    fun docMonologues(workspaceId: String, docId: String, apiKey: String? = null): DocMonologuesResult {
        return handleCall(api.getDocMonologues(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc topics
     */
    @JvmOverloads
    fun docTopics(workspaceId: String, docId: String, apiKey: String? = null): DocTopicsResult {
        return handleCall(api.getDocTopics(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc tags
     */
    @JvmOverloads
    fun docTags(workspaceId: String, docId: String, apiKey: String? = null): DocTagsResult {
        return handleCall(api.getDocTags(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Retrieves the doc screens
     */
    @JvmOverloads
    fun docScreens(workspaceId: String, docId: String, apiKey: String? = null): DocScreensResult {
        return handleCall(api.getDocScreens(currentApiKey(apiKey), workspaceId, docId))
    }

    /**
     * Creates a new speech recognition stream
     */
    @JvmOverloads
    fun streamCreate(apiKey: String? = null): Result<StreamInfo> {
        return handleCall(api.createStream(currentApiKey(apiKey)))
    }

    /**
     * List speech recognition streams
     */
    @JvmOverloads
    fun streamList(apiKey: String? = null): Results<StreamId> {
        return handleCall(api.listStream(currentApiKey(apiKey)))
    }

    /**
     * Check stream exists
     */
    @JvmOverloads
    fun streamExist(streamId: String, apiKey: String? = null): ObjectExist {
        return handleCall(api.existStream(currentApiKey(apiKey), streamId))
    }

    /**
     * Joins the notetaker to the external online meeting (Google Meet, Teams, Zoom)
     */
    @JvmOverloads
    fun meetingJoin(data: MeetingJoinData, apiKey: String?=null): Result<Meeting> {
        return handleCall(api.meetingJoin(currentApiKey(apiKey), data))
    }

    /**
     * List external meetings the notetaker has currently joined
     */
    @JvmOverloads
    fun meetingList(apiKey: String? = null): Results<Meeting> {
        return handleCall(api.meetingList(currentApiKey(apiKey)))
    }

    /**
     * Get the meeting metadata
     */
    @JvmOverloads
    fun meetingGet(meetingId: String, apiKey: String?=null): Result<Meeting> {
        return handleCall(api.meetingGet(currentApiKey(apiKey), meetingId))
    }

    /**
     * Check whether the meeting exists
     */
    @JvmOverloads
    fun meetingExist(meetingId: String, apiKey: String?=null): ObjectExist {
        return handleCall(api.meetingExist(currentApiKey(apiKey), meetingId))
    }

    /**
     * Uploads a media file to be analyzed by Hyperia AI
     */
    @JvmOverloads
    fun fileIngest(workspaceId: String, payload: IngestPayload, file: File, apiKey: String?=null): IngestId {
        return handleCall(api.ingestFile(currentApiKey(apiKey), workspaceId, payload, file))
    }

    /**
     * Crawls a HTTP or HTTPS media file to be analyzed by Hyperia AI
     */
    @JvmOverloads
    fun urlIngest(workspaceId: String, data: IngestUrlData, apiKey: String?=null): IngestId {
        return handleCall(api.ingestUrl(currentApiKey(apiKey), workspaceId, data))
    }

    private fun currentApiKey(apiKey: String?): String {
        return apiKey ?: this.apiKey ?: throw HyperiaClientException(APIKEY_REQUIRED)
    }
}