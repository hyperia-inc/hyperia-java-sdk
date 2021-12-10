package net.hyperia.example;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class ReceiverWSListener extends WebSocketListener {
    private final OutputStream stream;
    private volatile boolean closed = false;

    public ReceiverWSListener(Path filePath) throws IOException {
        stream = Files.newOutputStream(filePath, StandardOpenOption.WRITE);
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        System.out.println(Thread.currentThread().getId() + " ws connected " + response.code());
    }
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        byte[] content = text.getBytes();
        System.out.println(Thread.currentThread().getId() + " ws received " + content.length + " bytes");
        try {
            stream.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        System.out.println(Thread.currentThread().getId() + " ws received " + bytes.size() + " bytes");
        try {
            bytes.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.println(Thread.currentThread().getId() + " ws closed status: " + code + ", reason: " + reason);
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closed = true;
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        Optional.ofNullable(response).ifPresent(r -> System.out.println(r.code()));
        t.printStackTrace();
        webSocket.cancel();
        closed = true;
    }
}
