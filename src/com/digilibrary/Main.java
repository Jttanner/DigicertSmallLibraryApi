package com.digilibrary;

import com.digilibrary.ApiServer.IApiServer;
import com.digilibrary.ApiServer.SimpleApiServer;
import com.digilibrary.Logger.BasicLogger;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {



    public static void main(String[] args) {
        IApiServer apiServer = new SimpleApiServer(BasicLogger.getInstance());
        apiServer.run("8080");
    }
}
