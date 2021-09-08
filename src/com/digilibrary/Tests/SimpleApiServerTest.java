package com.digilibrary.Tests;

import com.digilibrary.ApiServer.IApiServer;
import com.digilibrary.ApiServer.SimpleApiServer;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class SimpleApiServerTest {

    private IApiServer apiServerInstance;

    @org.junit.Before
    public void setUp() {
        apiServerInstance = SimpleApiServer.getInstance();
    }

    @org.junit.After
    public void tearDown() {

    }


    @org.junit.jupiter.api.Test
    void getInstance() {
        assertNotNull(apiServerInstance);
    }

    @org.junit.jupiter.api.Test
    void run() {
        apiServerInstance.run("8080");
        assertNotNull(apiServerInstance);
    }

    @org.junit.jupiter.api.Test
    void stop() {
        apiServerInstance.stop();
        assertNull(apiServerInstance);
    }
}