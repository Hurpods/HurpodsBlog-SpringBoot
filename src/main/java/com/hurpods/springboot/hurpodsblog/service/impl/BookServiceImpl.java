package com.hurpods.springboot.hurpodsblog.service.impl;

import cn.hutool.http.HtmlUtil;
import com.hurpods.springboot.hurpodsblog.dao.BooksDAO;
import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.pojo.Category;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
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

    @Override
    public Result insertBook(Book requestBook) {
        Book book = escapeBook(requestBook);
        return booksDAO.insertBook(book) == 1 ?
                ResultFactory.buildSuccessResult("新增图书成功") :
                ResultFactory.buildFailureResult("未知错误，请稍后重试");
    }

    @Override
    public Result updateBook(Book requestBook) {
        Book book = escapeBook(requestBook);
        book.setBookId(requestBook.getBookId());
        return booksDAO.updateBook(book) == 1 ?
                ResultFactory.buildSuccessResult("修改图书成功") :
                ResultFactory.buildFailureResult("未知错误，请稍后重试");
    }

    @Override
    public Result deleteBook(Integer bookId) {
        return booksDAO.deleteBook(bookId) == 1 ?
                ResultFactory.buildSuccessResult("删除图书成功") :
                ResultFactory.buildFailureResult("未知错误，请稍后重试");
    }

    private Book escapeBook(Book book) {
        book.setBookCover(HtmlUtil.escape(book.getBookCover()));
        book.setBookTitle(HtmlUtil.escape(book.getBookTitle()));
        book.setBookAuthor(HtmlUtil.escape(book.getBookAuthor()));
        book.setPostDate(HtmlUtil.escape(book.getPostDate()));
        book.setBookPress(HtmlUtil.escape(book.getBookPress()));
        book.setBookAbs(HtmlUtil.escape(book.getBookAbs()));
        book.setCid(book.getCategory().getCategoryId());

        return book;
    }
}
