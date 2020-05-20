package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.pojo.Category;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    List<Category> getAllCats();

    List<Book> getBooksByCatId(Integer catId);

    List<Book> searchBooks(String keywords);
}
