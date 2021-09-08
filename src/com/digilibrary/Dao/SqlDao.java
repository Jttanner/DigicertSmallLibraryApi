package com.digilibrary.Dao;

import com.digilibrary.Dto.BookLibrary.Book;
import com.digilibrary.Dto.BookLibrary.FullLibrary;
import com.digilibrary.Logger.BasicLogger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//giving examples of how we could implement adao using sql
public class SqlDao implements IDao {
    public FullLibrary mockLibrary;
    private BasicLogger logger;
    public SqlDao(){
        mockLibrary = new FullLibrary();
        logger = BasicLogger.getInstance();
    }

    //example generic sqlite connection method
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public Book getBookByTitle(String bookTitle) {
        String query = "SELECT * FROM Books WHERE title = ? LIMIT 1";
        Book foundBook = null;
        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(query)){

            // set the param
            pstmt.setString(1,bookTitle);

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                foundBook = new Book(rs.getString("title"), rs.getString("author"), rs.getString("id"));
            }
        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
        }
        return foundBook;
    }

    @Override
    public FullLibrary getListOfBooksByTitles(List<String> bookTitles) {
        String query = "SELECT * FROM Books WHERE title IN " + CreateParamListForInQuery(bookTitles);
        FullLibrary foundBooks = new FullLibrary();
        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(query)){

            // set the value for each parameter
            for(int i = 0; i < bookTitles.size(); i++){
                pstmt.setString(i+1,bookTitles.get(i));
            }

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                foundBooks.addBook(new Book(rs.getString("title"), rs.getString("author"), rs.getString("id")));
            }
        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
        }
        return foundBooks;
    }

    @Override
    public FullLibrary getBooksByAuthor(String authorName) {
        String query = "SELECT * FROM Books WHERE author = ?";
        FullLibrary foundBooks = new FullLibrary();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){

             // set the param
             pstmt.setString(1,authorName);

             ResultSet rs  = pstmt.executeQuery();

             // loop through the result set
             while (rs.next()) {
                 foundBooks.addBook(new Book(rs.getString("title"), rs.getString("author"), rs.getString("id")));
             }
        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
        }
        return foundBooks;
    }

    @Override
    public FullLibrary getBooksByMultipleAuthors(List<String> authorNames) {
        //build the IN statement
        String query = "SELECT * FROM Books WHERE author IN " + CreateParamListForInQuery(authorNames);
        FullLibrary foundBooks = new FullLibrary();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){

            // set the value for each parameter
            for(int i = 0; i < authorNames.size(); i++){
                pstmt.setString(i+1,authorNames.get(i));
            }

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                foundBooks.addBook(new Book(rs.getString("title"), rs.getString("author"), rs.getString("id")));
            }
        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
        }
        return foundBooks;
    }

    @Override
    public FullLibrary getAllbooks() {
        String query = "SELECT * FROM Books";
        FullLibrary foundBooks = new FullLibrary();
        try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(query)){

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                foundBooks.addBook(new Book(rs.getString("title"), rs.getString("author"), rs.getString("id")));
            }
        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
        }
        return foundBooks;
    }

    @Override
    public Book addSingleBook(Book book) {
        String query = "INSERT INTO Books (title, author) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){

            // set the params
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
            return null;
        }
        return book;
    }

    @Override
    public FullLibrary addMultipleBooks(FullLibrary books) {
        String query = "INSERT INTO Books (title, author) VALUES " + createParamListForInsertMultiple(books.getBookList());
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){

            //set params list
            int i = 1;
            for(Book book : books.getBookList()){
                pstmt.setString(i++, book.getTitle());
                pstmt.setString(i++, book.getAuthor());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
            return null;
        }
        return books;
    }

    @Override
    public Book deleteSingleBook(Book book) {
        String query = "DELETE FROM Books WHERE id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){

            // set the params
            pstmt.setString(1, book.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
            return null;
        }
        return book;
    }

    @Override
    public Book updateSingleBook(Book book) {
        String query = "UPDATE Books SET title = ?, author = ? WHERE id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(query)){

            // set the params
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.LogException(e, "error getting books by title in sql dao");
            return null;
        }
        return book;
    }


    private String createParamListForInsertMultiple(List<Book> listOfParams){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < listOfParams.size(); i++){
            sb.append("(");
            sb.append(listOfParams.get(i).getTitle());
            sb.append(",");
            sb.append(listOfParams.get(i).getAuthor());
            sb.append(")");
            if (i != listOfParams.size() - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private String CreateParamListForInQuery(List<String> listOfParams){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(int i = 0; i < listOfParams.size(); i++){
            sb.append(listOfParams.get(i));
            if (i != listOfParams.size() - 1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }


}
