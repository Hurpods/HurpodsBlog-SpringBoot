package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.BooksDAO;
import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.pojo.Category;
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

    @Override
    public List<Category> getAllCats() {
        return booksDAO.getAllCats();
    }

    @Override
    public List<Book> getBooksByCatId(Integer catId) {
        return booksDAO.getBooksByCatId(catId);
    }

    @Override
    public List<Book> searchBooks(String keywords) {
        return booksDAO.searchBooks(keywords);
    }
}
