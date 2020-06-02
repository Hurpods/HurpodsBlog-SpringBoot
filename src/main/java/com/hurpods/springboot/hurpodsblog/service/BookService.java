package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.pojo.Category;
import com.hurpods.springboot.hurpodsblog.result.Result;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    List<Category> getAllCats();

    List<Book> getBooksByCatId(Integer catId);

    List<Book> searchBooks(String keywords);

    Result insertBook(Book requestBook);

    Result updateBook(Book requestBook);

    Result deleteBook(Integer bookId);
}
