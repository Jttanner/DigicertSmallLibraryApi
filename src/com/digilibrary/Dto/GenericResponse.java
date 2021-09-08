package com.digilibrary.Dto;

import com.digilibrary.Dto.BookLibrary.Book;
import com.digilibrary.Dto.BookLibrary.FullLibrary;

import java.util.ArrayList;

//for the sake of complexity I won't spend time making granular request/response objects
public class GenericResponse {
    public String requestType;
    public String message;
    public FullLibrary relatedBooks;

    public GenericResponse(String requestType, String message, FullLibrary relatedBooks) {
        this.requestType = requestType;
        this.message = message;
        this.relatedBooks = relatedBooks;
    }

    public FullLibrary getRelatedBooks() {
        return relatedBooks;
    }

    public void setRelatedBooks(FullLibrary relatedBooks) {
        this.relatedBooks = relatedBooks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
