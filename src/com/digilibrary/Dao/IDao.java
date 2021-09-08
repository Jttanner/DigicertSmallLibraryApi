package com.digilibrary.Dao;

import com.digilibrary.Dto.BookLibrary.Book;
import com.digilibrary.Dto.BookLibrary.FullLibrary;

import java.util.List;

public interface IDao {
    Book getBookByTitle(String bookTitle);
    FullLibrary getListOfBooksByTitles(List<String> bookTitles);
    FullLibrary getBooksByAuthor(String authorName);
    FullLibrary getBooksByMultipleAuthors(List<String> authorNames);
    FullLibrary getAllbooks();
    Book addSingleBook(Book book); //returns object used to add
    FullLibrary addMultipleBooks(FullLibrary book); //returns objects used to add
    Book deleteSingleBook(Book book); //returned deleted book
    Book updateSingleBook(Book book); //returns updated book
}
