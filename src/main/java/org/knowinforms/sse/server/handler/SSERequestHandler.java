package org.knowinforms.sse.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SSERequestHandler implements HttpHandler {

    private final List<OutputStream> sseOutputStreamList = new ArrayList<>();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestURI());
        exchange.getResponseHeaders().add("Content-Type", "text/event-stream");
        exchange.getResponseHeaders().add("charset", "utf-8");
        exchange.getResponseHeaders().add("access-control-allow-origin", "*");
        exchange.sendResponseHeaders(200, 0);
        sseOutputStreamList.add(exchange.getResponseBody());

        String response = """
                data: hello \n\n""";
        this.sendEventStream(response, exchange.getResponseBody());
    }

    public synchronized void sendEventStream(String message, OutputStream sseOutputStream) throws IOException {
        sseOutputStream.write(message.getBytes());
        sseOutputStream.flush();
        System.out.println(message);
    }

    public boolean isSSEClientConnected() {
        return !sseOutputStreamList.isEmpty();
    }

    public void sendEventPeriodically() throws IOException {
        int count = 0;
        try{
            while(this.isSSEClientConnected()) {
                int countFinal = count;
                this.sseOutputStreamList.forEach(os->
                {
                    try {
                        this.sendEventStream("data: Hello World!-"+ countFinal + " \n\n",os);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                Thread.sleep(60000);
                count++;
            }
        } catch (Exception err){
            err.printStackTrace();
        }
    }
}
