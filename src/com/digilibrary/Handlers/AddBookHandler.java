package com.digilibrary.Handlers;

import com.digilibrary.Dto.BookLibrary.Book;
import com.digilibrary.Dto.BookLibrary.FullLibrary;
import com.digilibrary.Dto.FailureResponse;
import com.digilibrary.Dto.GenericBooksRequest;
import com.digilibrary.Dto.GenericResponse;
import com.digilibrary.Logger.BasicLogger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class AddBookHandler extends BaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        OutputStream respBody = httpExchange.getResponseBody();
        try {
            if (httpExchange.getRequestMethod().equalsIgnoreCase("put")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                InputStream reqBody = httpExchange.getRequestBody();
                String reqData = readString(reqBody);

                GenericBooksRequest addItemRequest = gson.fromJson(reqData, GenericBooksRequest.class);
                GenericResponse response;
                FullLibrary addedBooks;

                try {
                    switch (addItemRequest.requestType){
                        case "addSingle":
                            Book addedBook = dao.addSingleBook(addItemRequest.relatedBooks.getBookList().get(0));
                            if (addedBook == null)
                                throw new Exception("Bad addBook request");
                            addedBooks = new FullLibrary();
                            addedBooks.addBook(addedBook);
                            response = new GenericResponse(addItemRequest.requestType, "success", addedBooks);
                            break;
                        case "addMultiple":
                            addedBooks = dao.addMultipleBooks(addItemRequest.relatedBooks);
                            if (addedBooks == null)
                                throw new Exception("Bad addBook request");
                            response = new GenericResponse(addItemRequest.requestType, "success", addedBooks);
                            break;
                        default:
                            throw new Exception("Bad add request");
                    }

                    encode(response,respBody);
                    respBody.close();

                }
                catch (Exception e){
                    BasicLogger.getInstance().LogException(e, "failed in add book near dao operations");
                    throw e;
                }
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                respBody.close();
            }
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            encode(new FailureResponse("Add Book request failed"), respBody);
            respBody.close();
            BasicLogger.getInstance().LogException(e, e.getMessage());
        }

        //add one
        //add some
    }
}
