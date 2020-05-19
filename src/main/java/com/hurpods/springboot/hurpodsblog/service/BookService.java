package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
}
