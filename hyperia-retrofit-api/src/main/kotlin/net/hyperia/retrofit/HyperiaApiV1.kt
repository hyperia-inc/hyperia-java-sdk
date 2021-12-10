package net.hyperia.retrofit

import net.hyperia.*
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface HyperiaApiV1 {

    @PUT("/v1/stream/create")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun createStream(@Header("apikey") apikey: String,
                     @Body body: Any = Object()): Call<Result<StreamInfo>>
    @GET("/v1/stream/list")
    fun listStream(@Header("apikey") apikey: String): Call<Results<StreamId>>
    @GET("/v1/stream/id/{streamId}/exists")
    fun existStream(@Header("apikey") apikey: String, @Path("streamId") streamId: String): Call<ObjectExist>


    @PUT("/v1/meeting/join")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun meetingJoin(@Header("apikey") apikey: String,
                    @Body data: MeetingJoinData): Call<Result<Meeting>>

    @GET("/v1/meeting/list")
    fun meetingList(@Header("apikey") apikey: String):
            Call<Results<Meeting>>

    @GET("/v1/meeting/id/{meetingId}/metadata/info")
    fun meetingGet(@Header("apikey") apikey: String,
                   @Path("meetingId") meetingId: String): Call<Result<Meeting>>

    @GET("/v1/meeting/id/{meetingId}/exists")
    fun meetingExist(@Header("apikey") apikey: String,
                     @Path("meetingId") meetingId: String): Call<ObjectExist>

    @Multipart
    @PUT("/v1/workspace/id/{workspaceId}/ingest/file")
    fun ingestFile(@Header("apikey") apikey: String,
                   @Path("workspaceId") workspaceId: String,
                   @Part("payload\";filename=\"payload.json") payload: IngestPayload,
                   @Part("file\";filename=\"file") file: File
    ): Call<IngestId>

    @PUT("/v1/workspace/id/{workspaceId}/ingest/url")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun ingestUrl(@Header("apikey") apikey: String,
                  @Path("workspaceId") workspaceId: String,
                  @Body data: IngestUrlData
    ): Call<IngestId>

    @GET("/v1/workspace/list")
    fun listWorkspaces(@Header("apikey") apikey: String): Call<Results<Workspace>>

    @GET("/v1/workspace/public/list")
    fun listPublicWorkspaces(@Header("apikey") apikey: String): Call<Results<Workspace>>

    @PUT("/v1/management/workspace/create")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun createWorkspace(@Header("apikey") apikey: String, @Body data: WorkspaceData): Call<Result<WorkspaceId>>

    @DELETE("/v1/management/workspace/delete/{workspaceId}")
    fun deleteWorkspace(@Header("apikey") apikey: String, @Path("workspaceId") workspaceId: String): Call<Status>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/exists")
    fun existDoc(@Header("apikey") apikey: String,
                 @Path("workspaceId") workspaceId: String,
                 @Path("docId") docId: String): Call<ObjectExist>

    @DELETE("/v1/workspace/id/{workspaceId}/doc/{docId}")
    fun deleteDoc(@Header("apikey") apikey: String,
                  @Path("workspaceId") workspaceId: String,
                  @Path("docId") docId: String): Call<Status>

    @PUT("/v1/workspace/id/{workspaceId}/doc/list")
    fun listDoc(@Header("apikey") apikey: String,
                @Path("workspaceId") workspaceId: String): Call<DocsResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/info")
    fun getDocInfo(@Header("apikey") apikey: String,
                   @Path("workspaceId") workspaceId: String,
                   @Path("docId") docId: String): Call<DocInfoResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/transcript")
    fun getDocTranscript(@Header("apikey") apikey: String,
                         @Path("workspaceId") workspaceId: String,
                         @Path("docId") docId: String): Call<DocTranscriptResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/summary")
    fun getDocSummary(@Header("apikey") apikey: String,
                      @Path("workspaceId") workspaceId: String,
                      @Path("docId") docId: String): Call<DocSummaryResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/monologues")
    fun getDocMonologues(@Header("apikey") apikey: String,
                         @Path("workspaceId") workspaceId: String,
                         @Path("docId") docId: String): Call<DocMonologuesResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/screens")
    fun getDocScreens(@Header("apikey") apikey: String,
                      @Path("workspaceId") workspaceId: String,
                      @Path("docId") docId: String): Call<DocScreensResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/topics")
    fun getDocTopics(@Header("apikey") apikey: String,
                     @Path("workspaceId") workspaceId: String,
                     @Path("docId") docId: String): Call<DocTopicsResult>

    @GET("/v1/workspace/id/{workspaceId}/doc/id/{docId}/metadata/tags")
    fun getDocTags(@Header("apikey") apikey: String,
                   @Path("workspaceId") workspaceId: String,
                   @Path("docId") docId: String): Call<DocTagsResult>


    @PUT("/v1/workspace/id/{workspaceId}/doc/ranked/topic")
    fun getTopRankedTopics(@Header("apikey") apikey: String,
                           @Path("workspaceId") workspaceId: String): Call<TopicsResult>

    @PUT("/v1/workspace/id/{workspaceId}/doc/ranked/topic")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun getTopRankedTopics(@Header("apikey") apikey: String,
                           @Path("workspaceId") workspaceId: String,
                           @Body data: DocSearchFilter): Call<TopicsResult>

    @PUT("/v1/workspace/id/{workspaceId}/doc/ranked/speaker")
    fun getTopRankedSpeakers(@Header("apikey") apikey: String,
                             @Path("workspaceId") workspaceId: String): Call<SpeakersResult>

    @PUT("/v1/workspace/id/{workspaceId}/doc/ranked/speaker")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun getTopRankedSpeakers(@Header("apikey") apikey: String,
                             @Path("workspaceId") workspaceId: String,
                             @Body data: DocSearchFilter): Call<SpeakersResult>

    @PUT("/v1/workspace/id/{workspaceId}/doc/ranked/tag")
    fun getTopRankedTags(@Header("apikey") apikey: String,
                         @Path("workspaceId") workspaceId: String): Call<TagsResult>

    @PUT("/v1/workspace/id/{workspaceId}/doc/ranked/tag")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun getTopRankedTags(@Header("apikey") apikey: String,
                         @Path("workspaceId") workspaceId: String,
                         @Body data: DocSearchFilter): Call<TagsResult>


    @PUT("/v1/workspace/id/{workspaceId}/doc/search/transcript")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun searchTranscript(@Header("apikey") apikey: String,
                         @Path("workspaceId") workspaceId: String,
                         @Body data: TranscriptSearchFilter): Call<TranscriptsResult>
}