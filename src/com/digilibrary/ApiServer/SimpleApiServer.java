package com.digilibrary.ApiServer;


import com.digilibrary.Handlers.AddBookHandler;
import com.digilibrary.Handlers.DeleteBookHandler;
import com.digilibrary.Handlers.GetBookHandler;
import com.digilibrary.Handlers.UpdateBookHandler;
import com.digilibrary.Logger.ILogger;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class SimpleApiServer implements IApiServer {

    private HttpServer httpServer;
    private ILogger logger;

    public SimpleApiServer(HttpServer httpServer, ILogger logger){
        this.httpServer = httpServer;
        this.logger = logger;
    }

    @Override
    public void run() {

        System.out.println("Initializing HTTP Server");



        // apparently required for HttpServer package, anything other than null not needed for this
        httpServer.setExecutor(null);

        //register the paths we need to listen to
        logger.LogInfo("Registering Handlers");

        httpServer.createContext("/AddBook", new AddBookHandler());
        httpServer.createContext("/DeleteBook", new DeleteBookHandler());
        httpServer.createContext("/GetBook", new GetBookHandler());
        httpServer.createContext("/UpdateBook", new UpdateBookHandler());


        logger.LogInfo("starting server");

        //Starts the server to run in the background.
        httpServer.start();

        logger.LogInfo("server started");
    }

    @Override
    public void stop() {
        httpServer.stop(0);
        httpServer = null;
    }
}
