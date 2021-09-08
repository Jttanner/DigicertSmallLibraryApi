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

public class UpdateBookHandler extends BaseHandler implements HttpHandler {
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
                FullLibrary updatedBooks = new FullLibrary();

                try {
                    switch (addItemRequest.requestType){
                        case "updateSingle":
                            Book updatedBook = dao.updateSingleBook(addItemRequest.relatedBooks.getBookList().get(0));
                            if (updatedBook == null)
                                throw new Exception("Bad updateSingle request");
                            updatedBooks.addBook(updatedBook);
                            response = new GenericResponse(addItemRequest.requestType, "success", updatedBooks);
                            break;
                        case "updateMultiple": //a better structure would be to use a specific request object to help us do bulk updates
                            for(Book bookToUpdate : addItemRequest.relatedBooks.getBookList()){
                                Book currUpdatedBook = dao.updateSingleBook(bookToUpdate);
                                updatedBooks.addBook(currUpdatedBook);
                                if (currUpdatedBook == null)
                                    BasicLogger.getInstance().LogInfo("failed to update a book in a bulk update");
                            }
                            if (updatedBooks.getBookList().size() == 0){
                                throw new Exception("Bad updateMultiple request");
                            }
                            response = new GenericResponse(addItemRequest.requestType, "success", updatedBooks);
                            break;
                        default:
                            throw new Exception("Bad update request");
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
    }

}
