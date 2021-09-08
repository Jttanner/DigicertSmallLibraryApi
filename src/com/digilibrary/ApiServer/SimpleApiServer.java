package com.digilibrary.ApiServer;


import com.digilibrary.Logger.ILogger;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class SimpleApiServer implements IApiServer {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer httpServer;
    private ILogger logger;

    public SimpleApiServer(ILogger logger){
        this.logger = logger;
    }

    @Override
    public void run(String port) {

        System.out.println("Initializing HTTP Server");

        try {
            // Create the HttpServer object
            httpServer = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(port)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // apparently required for HttpServer package, anything other than null not needed for this
        httpServer.setExecutor(null);

        //register the paths we need to listen to
        logger.LogInfo("Registering Handlers");

        //httpServer.createContext("path", new HandlerObject());

        logger.LogInfo("starting server");

        //Starts the server to run in the background.
        httpServer.start();

        logger.LogInfo(String.format("server started and listening on port %s", port));
    }

    @Override
    public void stop() {
        httpServer.stop(0);
        httpServer = null;
    }
}
