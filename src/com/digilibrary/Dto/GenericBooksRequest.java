package com.digilibrary.Dto;
import com.digilibrary.Dto.BookLibrary.Book;

import java.util.ArrayList;

//for the sake of complexity I won't spend time making granular request/response objects and will have the request type as a string on this object instead
public class GenericBooksRequest {
    public String requestType;
    public ArrayList<Book> relatedBooks;

    public GenericBooksRequest(String requestType, ArrayList<Book> relatedBooks) {
        this.requestType = requestType;
        this.relatedBooks = relatedBooks;
    }

    public ArrayList<Book> getRelatedBooks() {
        return relatedBooks;
    }

    public void setRelatedBooks(ArrayList<Book> relatedBooks) {
        this.relatedBooks = relatedBooks;
    }
}
