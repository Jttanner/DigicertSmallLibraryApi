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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetBookHandler extends BaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange)  throws IOException {

        OutputStream respBody = httpExchange.getResponseBody();
        try {
            if (httpExchange.getRequestMethod().equalsIgnoreCase("put")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                InputStream reqBody = httpExchange.getRequestBody();
                String reqData = readString(reqBody);

                GenericBooksRequest addItemRequest = gson.fromJson(reqData, GenericBooksRequest.class);
                GenericResponse response;
                FullLibrary foundBooks;

                try {
                    switch (addItemRequest.requestType){
                        case "getAllBooks":
                            foundBooks = dao.getAllbooks();
                            if (foundBooks == null)
                                throw new Exception("Bad getAllBooks request");
                            response = new GenericResponse(addItemRequest.requestType, "success", foundBooks);
                            break;
                        case "getBooksByAuthor":
                            foundBooks = dao.getBooksByAuthor(addItemRequest.relatedBooks.getBookList().get(0).getAuthor()); //could use refactoring with specific request/response objects to clean this up
                            if (foundBooks == null)
                                throw new Exception("Bad getBooksByAuthor request");
                            response = new GenericResponse(addItemRequest.requestType, "success", foundBooks);
                            break;
                        case "getBooksByMultipleAuthors":
                            //query the list of objects down to the list of authors
                            //essentially books.author each added to a list of strings
                            List<String> authorList = addItemRequest.relatedBooks.getBookList()
                                    .stream()
                                    .map(Book::getAuthor)
                                    .collect((Collectors.toList()));
                            foundBooks = dao.getBooksByMultipleAuthors(authorList);
                            if (foundBooks == null)
                                throw new Exception("Bad getBooksByMultipleAuthors request");
                            response = new GenericResponse(addItemRequest.requestType, "success", foundBooks);
                            break;
                        case "getBookByTitle":
                            foundBooks = dao.getBooksByAuthor(addItemRequest.relatedBooks.getBookList().get(0).getTitle());
                            if (foundBooks == null)
                                throw new Exception("Bad getBookByTitle request");
                            response = new GenericResponse(addItemRequest.requestType, "success", foundBooks);
                            break;
                        case "getListOfBooksByTitles":
                            //query the list of objects down to the list of titles
                            //essentially books.title each added to a list of strings
                            List<String> titleList = addItemRequest.relatedBooks.getBookList()
                                    .stream()
                                    .map(Book::getTitle)
                                    .collect((Collectors.toList()));
                            foundBooks = dao.getBooksByMultipleAuthors(titleList);
                            if (foundBooks == null)
                                throw new Exception("Bad getListOfBooksByTitles request");
                            response = new GenericResponse(addItemRequest.requestType, "success", foundBooks);
                            break;
                        default:
                            throw new Exception("Bad AddBook request");
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
