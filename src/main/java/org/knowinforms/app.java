package org.knowinforms;

import org.knowinforms.sse.client.SSEClient;
import org.knowinforms.sse.server.SSEServer;

import java.io.IOException;

public class app {
    private static SSEServer sseServer = null;
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to SSE server and Client!");

        sseServer = new SSEServer();
        try{
            startSSEClient();
            while(!sseServer.getRequestHandler().isSSEClientConnected()){
                System.out.println("Waiting for Client SSE Event stream request...");
                Thread.sleep(1000);
            }
            sseServer.getRequestHandler().sendEventPeriodically();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void startSSEClient() {
        String sseServerUrl = "http://localhost:8080/sse-stream";
        SSEClient sseClient = new SSEClient();
        sseClient.connect(sseServerUrl);
    }
}