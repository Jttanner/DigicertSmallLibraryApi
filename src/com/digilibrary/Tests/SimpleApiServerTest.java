package com.digilibrary.Tests;

import com.digilibrary.ApiServer.IApiServer;
import com.digilibrary.ApiServer.SimpleApiServer;
import com.digilibrary.Logger.BasicLogger;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleApiServerTest {

    private IApiServer apiServerInstance;
    private HttpServer mockHttpServer;

    @BeforeEach
    public void setUp() {
        apiServerInstance = new SimpleApiServer(new MockHttpServer(), BasicLogger.getInstance());
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void runTest() {
        apiServerInstance.run();
        assertNotNull(apiServerInstance);
    }

    @Test
    void stopTest() {
        apiServerInstance.stop();
        assertNull(apiServerInstance);
    }
}