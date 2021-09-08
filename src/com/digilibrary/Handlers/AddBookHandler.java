package com.digilibrary.Handlers;

import com.digilibrary.Dto.FailureResponse;
import com.digilibrary.Dto.GenericBooksRequest;
import com.digilibrary.Dto.GenericResponse;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.beans.Encoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class AddBookHandler extends BaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        OutputStream respBody = httpExchange.getResponseBody();
        Encoder encoder = new Encoder();
        try {
            if (httpExchange.getRequestMethod().equalsIgnoreCase("put")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                InputStream reqBody = httpExchange.getRequestBody();
                String reqData = readString(reqBody);
                Gson gson = new Gson();

                GenericBooksRequest registerRequest = gson.fromJson(reqData, GenericBooksRequest.class);
                GenericResponse response;
                try {
                    response = null;//TODO: implement dao
                    encode(response,respBody);
                    respBody.close();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                respBody.close();
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            encode(new FailureResponse("Add Book request failed"), respBody);
            respBody.close();
            e.printStackTrace();
        }

        //add one
        //add some
    }
}
