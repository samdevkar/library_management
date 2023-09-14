package com.example.library_management.service;


import com.example.library_management.dto.CreateBookRequest;
import com.example.library_management.model.Author;
import com.example.library_management.model.Book;
import com.example.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;
    @Autowired
    BookRepository bookRepository;

    public void createOrUpdateBook(Book book){
        Author bookAuthor=book.getBook_author();
        Author existingAuthor=authorService.getOrCreate(bookAuthor);
        book.setBook_author(existingAuthor);
        bookRepository.save(book);

    }
    public List<Book> findBook(String searchKey, String searchValue) throws Exception{
        switch (searchKey) {
            case "name" -> {
                return bookRepository.findByName(searchValue);
            }
            case "author_name" -> {
                return bookRepository.findByAuthorName(searchValue);
            }
            case "genre" -> {
                return bookRepository.findByGenre(searchValue);
            }
            case "id" -> {
                Optional<Book> book = bookRepository.findById(Integer.parseInt(searchValue));
                return book.map(Arrays::asList).orElseGet(ArrayList::new);
            }
            default -> throw new Exception("Search key is not Valid" + searchKey);
        }
    }
}
