package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BooksDAO {
    List<Book> getAllBooks();
}
