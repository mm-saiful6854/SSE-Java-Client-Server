package org.knowinforms.sse.client;

import java.net.http.HttpClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SSEClient {

    private final HttpClient client;

    public SSEClient() {
        this.client = HttpClient.newHttpClient();
    }

    public void connect(String url) {
        try {
            // Create a request to the SSE server
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "text/event-stream")
                    .build();

            // Use a listener to process the response stream
            client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                    .thenAccept(response -> {
                        System.out.println("Connected to SSE server.");
                        response.body().forEach(this::handleEvent);
                    })
                    .exceptionally(e -> {
                        System.err.println("Error connecting to SSE server: " + e.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            System.err.println("Exception while connecting to SSE server: " + e.getMessage());
        }
    }

    private void handleEvent(String event) {
        if (!event.isBlank()) {
            System.out.println("HttpClient:: Received event: " + event);
        }
    }
}

