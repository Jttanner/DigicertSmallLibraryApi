package com.digilibrary.Dto;

import com.digilibrary.Dto.BookLibrary.Book;

import java.util.ArrayList;

//for the sake of complexity I won't spend time making granular request/response objects
public class GenericResponse {
    public String requestType;
    public String message;
    public ArrayList<Book> relatedBooks;

    public GenericResponse(String requestType, String message, ArrayList<Book> relatedBooks) {
        this.requestType = requestType;
        this.message = message;
        this.relatedBooks = relatedBooks;
    }

    public ArrayList<Book> getRelatedBooks() {
        return relatedBooks;
    }

    public void setRelatedBooks(ArrayList<Book> relatedBooks) {
        this.relatedBooks = relatedBooks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
