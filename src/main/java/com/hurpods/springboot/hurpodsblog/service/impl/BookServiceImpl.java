package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.BooksDAO;
import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BooksDAO booksDAO;

    @Override
    public List<Book> getAllBooks() {
        return booksDAO.getAllBooks();
    }
}
