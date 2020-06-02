package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksDAO {
    List<Book> getAllBooks();

    List<Category> getAllCats();

    List<Book> getBooksByCatId(@Param("catId") Integer catId);

    List<Book> searchBooks(@Param("keywords") String keywords);

    int insertBook(Book book);

    int updateBook(Book book);

    int deleteBook(@Param("bookId") Integer bookId);
}
