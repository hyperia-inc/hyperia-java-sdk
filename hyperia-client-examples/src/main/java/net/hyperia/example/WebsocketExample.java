package net.hyperia.example;

import net.hyperia.HyperiaClient;
import net.hyperia.Result;
import net.hyperia.StreamInfo;
import net.hyperia.retrofit.HyperiaApiFactory;
import net.hyperia.retrofit.HyperiaApiV1;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.hyperia.retrofit.HyperiaApiFactoryKt.defaultHttpClient;

public class WebsocketExample {

    public static void main(String[] args) throws IOException {
        System.out.println("Api base url " + args[0]);
        System.out.println("Api key " + args[1]);
        System.out.println("Stream file " + args[2]);
        OkHttpClient http = defaultHttpClient();
        HyperiaApiV1 api = new HyperiaApiFactory().v1(args[0], http);
        String apiKey = args[1];
        HyperiaClient client = new HyperiaClient(api, apiKey);

        Result<StreamInfo> res = client.streamCreate();
        String audioSocket = res.getResult().getAudioSocket();
        String eventSocket = res.getResult().getEventSocket();

        Path receiverTarget = Files.createTempFile("ws_receiver", "");
        System.out.println("The output file is: " + receiverTarget.toString());
        ReceiverWSListener receiverWSListener = new ReceiverWSListener(receiverTarget);
        WebSocket receiverWS = http.newWebSocket(new Request.Builder().url(eventSocket).build(), receiverWSListener);

        Path senderSource = Paths.get(args[2]);
        SenderWSListener senderWSListener = new SenderWSListener();
        WebSocket senderWS = http.newWebSocket(new Request.Builder().url(audioSocket).build(), senderWSListener);

        try {
            Thread fileSendThread = new Thread(() -> sendFile(senderWS, senderSource));
            fileSendThread.start();
            fileSendThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!senderWSListener.isClosed()) {
            senderWS.close(1000, "Stream ended");
        }
        System.out.println("Received " + receiverTarget.toFile().length() + " bytes from event socket");
        if (!receiverWSListener.isClosed()) {
            receiverWS.close(1000, "Stream ended");
        }
    }

    public static void sendFile(WebSocket ws, Path filePath) {
        int bufferSize = 640;
        byte[] buffer = new byte[bufferSize];
        try (InputStream is = new FileInputStream(filePath.toFile())) {
            int count = is.read(buffer);
            while (count != -1) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (ws.send(ByteString.of(buffer, 0, count))) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.out.println(Thread.currentThread().getId() + " ws failed to enqueue " + count + " bytes, force closing");
                    ws.cancel();
                    break;
                }
                count = is.read(buffer);
            }
            ws.close(1000, "Stream ended");
        } catch (IOException e) {
            e.printStackTrace();
            ws.cancel();
        }
    }
}
