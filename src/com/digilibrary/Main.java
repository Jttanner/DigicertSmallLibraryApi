package com.digilibrary;

import com.digilibrary.ApiServer.IApiServer;
import com.digilibrary.ApiServer.SimpleApiServer;
import com.digilibrary.Logger.BasicLogger;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private static final String defaultPort = "8080";
    private static HttpServer httpServer;

    public static void main(String[] args) {

        try {
            // Create the HttpServer object
            httpServer = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(args.length == 0 ? defaultPort : args[0])),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        IApiServer apiServer = new SimpleApiServer(httpServer, BasicLogger.getInstance());
        apiServer.run();
    }
}
