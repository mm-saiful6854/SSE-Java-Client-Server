package org.knowinforms.sse.server;

import com.sun.net.httpserver.HttpServer;
import org.knowinforms.sse.server.handler.SSERequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SSEServer {

    private final HttpServer server;
    private final SSERequestHandler requestHandler = new SSERequestHandler();
    public SSEServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080),0);
        // Define a context and its handler
        server.createContext("/sse-stream", requestHandler);

        // Start the server
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server started on port: " + server.getAddress().getPort());
    }

    public SSERequestHandler getRequestHandler() {
        return requestHandler;
    }
}
