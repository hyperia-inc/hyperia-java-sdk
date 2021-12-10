package net.hyperia.example;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

public class SenderWSListener extends WebSocketListener {
    private volatile boolean closed = false;

    public boolean isClosed() {
        return closed;
    }

    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        System.out.println(Thread.currentThread().getId() + " ws connected " + response.code());
    }

    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        byte[] content = text.getBytes();
        System.out.println(Thread.currentThread().getId() + " ws received " + content.length + " bytes");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        System.out.println(Thread.currentThread().getId() + "ws received " + bytes.size() + " bytes");
    }

    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.println(Thread.currentThread().getId() + "ws closed status: " + code + ", reason: " + reason);
        closed = true;
    }

    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        System.out.println(Thread.currentThread().getId() + " failure " + response.code());
        t.printStackTrace();
        webSocket.cancel();
        closed = true;
    }
}
