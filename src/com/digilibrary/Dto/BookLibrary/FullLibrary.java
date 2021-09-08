package com.digilibrary.Dto.BookLibrary;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;

public class FullLibrary {
    private ArrayList<Book> bookList;

    public FullLibrary(){
        bookList = new ArrayList<>();
    }

    //add a single book to the array
    public void addBook(Book book) {
        bookList.add(book); //handle if we are fine with duplicates in query
    }

    //add muiltiple books to the array
    public void addBookRange(ArrayList<Book> books) {
        bookList.addAll(books); //handle if we are fine with duplicates in query
    }
}
