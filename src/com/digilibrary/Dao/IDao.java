package com.digilibrary.Dao;

import com.digilibrary.Dto.BookLibrary.Book;
import com.digilibrary.Dto.BookLibrary.FullLibrary;

import java.util.ArrayList;

public interface IDao {
    Book getBookByTitle(String bookTitle);
    FullLibrary getListOfBooksByTitles(ArrayList<String> bookTitles);
    FullLibrary getBooksByAuthor(String authorName);
    FullLibrary getBooksByMultipleAuthors(ArrayList<String> authorNames);
    FullLibrary getAllbooks();
    Book AddSingleBook(Book book); //returns object used to add
    FullLibrary AddMultipleBooks(FullLibrary book); //returns objects used to add
}
