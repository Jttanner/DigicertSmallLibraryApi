package com.digilibrary.Dto;
import com.digilibrary.Dto.BookLibrary.Book;
import com.digilibrary.Dto.BookLibrary.FullLibrary;

import java.util.ArrayList;

//for the sake of complexity I won't spend time making granular request/response objects and will have the request type as a string on this object instead
public class GenericBooksRequest {
    public String requestType;
    public FullLibrary relatedBooks;

    public GenericBooksRequest(String requestType, FullLibrary relatedBooks) {
        this.requestType = requestType;
        this.relatedBooks = relatedBooks;
    }

    public FullLibrary getRelatedBooks() {
        return relatedBooks;
    }

    public void setRelatedBooks(FullLibrary relatedBooks) {
        this.relatedBooks = relatedBooks;
    }
}
