package net.hyperia.example;

import net.hyperia.*;
import net.hyperia.retrofit.HyperiaApiFactory;
import net.hyperia.retrofit.HyperiaApiV1;
import okhttp3.OkHttpClient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.List;

import static net.hyperia.retrofit.HyperiaApiFactoryKt.defaultHttpClient;

public class SimpleHyperiaClientExample {
    private final HyperiaClient client;

    public SimpleHyperiaClientExample() {
        HyperiaApiV1 api = new HyperiaApiFactory().v1("https://api.hyperia.net");

        String apiKey = "Configured Api Key";
        client = new HyperiaClient(api, apiKey);
    }

    public void onDemandApiKey() {
        HyperiaApiV1 api = new HyperiaApiFactory().v1("https://api.hyperia.net");

        HyperiaClient client = new HyperiaClient(api);

        client.workspaceList("Api Key 1");
        client.workspaceList("Api Key 2");
    }

    public void onDemandHttpOptions() {
        //Build your own instance with necessary timeouts, connection pool size etc
        OkHttpClient httpClient = defaultHttpClient();
        HyperiaApiV1 api = new HyperiaApiFactory().v1("https://api.hyperia.net", httpClient);
        HyperiaClient client = new HyperiaClient(api);
    }

    public void createWorkspace() {
        Result<WorkspaceId> res = client.workspaceCreate("test");
        System.out.println(res.getResult().getWorkspaceId());
    }

    public void listWorkspaces() {
        Results<Workspace> res = client.workspaceList();
        for (Workspace workspace: res.getResults()) {
            System.out.println(workspace);
        }
    }

    public void listPublicWorkspaces() {
        Results<Workspace> res = client.publicWorkspaceList();
        for (Workspace workspace: res.getResults()) {
            System.out.println(workspace);
        }
    }

    public void deleteWorkspace() {
        String workspaceId = "Workspace Id";
        Status res = client.workspaceDelete(workspaceId);
        System.out.println(res.getStatus());
    }

    public void checkDocExist() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        ObjectExist res = client.docExists(workspaceId, docId);
        System.out.println(res.getExists());
    }

    public void deleteDoc() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        Status res = client.docDelete(workspaceId, docId);
        System.out.println(res.getStatus());
    }

    public void retrieveRankedSpeakers() {
        String workspaceId = "Workspace Id";
        SpeakersResult res = client.docRankedSpeaker(workspaceId);
        for (Speaker speaker: res.getSpeakers()) {
            System.out.println(speaker);
        }
    }

    public void retrieveRankedTopics() {
        String workspaceId = "Workspace Id";
        TopicsResult res = client.docRankedTopic(workspaceId);
        for (Topic topic: res.getTopics()) {
            System.out.println(topic);
        }
    }

    public void retrieveRankedTags() {
        String workspaceId = "Workspace Id";
        TagsResult res = client.docRankedTag(workspaceId);
        for (Tag tag: res.getTags()) {
            System.out.println(tag);
        }
    }

    public void retrieveRankedTagsWithFilter() {
        String workspaceId = "Workspace Id";
        List<String> titlePhraseMatchList = Arrays.asList("value1", "value2");
        List<String> speakerList = Arrays.asList("speaker1", "speaker2");
        DocSearchFilter filter = new DocSearchFilter(
            titlePhraseMatchList, null, speakerList, null, null, null, null, null);
        TagsResult res = client.docRankedTag(workspaceId, filter);
        for (Tag tag: res.getTags()) {
            System.out.println(tag);
        }
    }

    public void transcriptSearchFilter() {
        String workspaceId = "Workspace Id";
        List<String> utterancePatternMatch = Arrays.asList("value1", "value2");
        List<String> speakerList = Arrays.asList("speaker1", "speaker2");
        DateRange dateRange = new DateRange("+03:00", LocalDate.now().minusDays(10), LocalDate.now());
        TranscriptSearchFilter filter = new TranscriptSearchFilter(
            utterancePatternMatch, speakerList, null, dateRange, null, null, null);
        TranscriptsResult res = client.docSearchTranscript(workspaceId, filter);
        for (Transcript transcript: res.getResults()) {
            System.out.println(transcript);
        }
    }

    public void docMetadataInfo() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocInfoResult res = client.docInfo(workspaceId, docId);
        System.out.println(res.getInfo());
    }

    public void docTranscript() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocTranscriptResult res = client.docTranscript(workspaceId, docId);
        System.out.println(res.getTranscript());
    }

    public void docSummary() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocSummaryResult res = client.docSummary(workspaceId, docId);
        System.out.println(res.getSummary());
    }

    public void docMonologues() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocMonologuesResult res = client.docMonologues(workspaceId, docId);
        for (DocMonologue monologue: res.getMonologues()) {
            System.out.println(monologue);
        }
    }

    public void docScreenshots() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocScreensResult res = client.docScreens(workspaceId, docId);
        for (DocScreen screen: res.getScreens()) {
            System.out.println(screen);
        }
    }

    public void docTopics() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocTopicsResult res = client.docTopics(workspaceId, docId);
        for (DocTopic topic: res.getTopics()) {
            System.out.println(topic);
        }
    }

    public void docTags() {
        String workspaceId = "Workspace Id";
        String docId = "Doc Id";
        DocTagsResult res = client.docTags(workspaceId, docId);
        for (DocTag tag: res.getTags()) {
            System.out.println(tag);
        }
    }

    public void streamCreate() {
        String workspaceId = "Workspace Id";
        Result<StreamInfo> res = client.streamCreate(workspaceId);
        System.out.println(res.getResult());
    }

    public void joinMeeting() {
        MeetingJoinData data = new MeetingJoinData("https://meeting.com/123456");
        Result<Meeting> res = client.meetingJoin(data);
        System.out.println(res.getResult());
    }

    public void listMeetings() {
        Results<Meeting> res = client.meetingList();
        for (Meeting meeting: res.getResults()) {
            System.out.println(meeting);
        }
    }

    public void meetingExists() {
        ObjectExist res = client.meetingExist("meetingId");
        System.out.println(res.getExists());
    }

    public void streamExists() {
        ObjectExist res = client.streamExist("streamId");
        System.out.println(res.getExists());
    }

    public void streamLIst() {
        Results<StreamId> streams = client.streamList();
        for (StreamId streamId : streams.getResults()) {
            System.out.println(streamId);
        }
    }

    public void ingestFile() {
        String workspaceId = "Workspace Id";
        IngestPayload payload = new IngestPayload("Ingest Title", LocalDate.now(), OffsetTime.now());
        File content = new File("/path/to/content");
        IngestId res = client.fileIngest(workspaceId, payload, content);
        System.out.println(res);
    }

    public void ingestUrl() throws MalformedURLException {
        String workspaceId = "Workspace Id";
        IngestUrlData data = new IngestUrlData("Ingest Title", new URL("https://domain.com/test/path"));
        IngestId res = client.urlIngest(workspaceId, data);
        System.out.println(res);
    }
}
