package com.example.library_management.controller;

import com.example.library_management.dto.CreateBookRequest;
import com.example.library_management.dto.SearchRequest;
import com.example.library_management.model.Book;
import com.example.library_management.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping(value="/newbook")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        bookService.createOrUpdateBook(createBookRequest.toBook());
    }
    @GetMapping("/getbooks")
    public List<Book> getBooks(@RequestBody @Valid SearchRequest searchRequest) throws Exception{
        List<Book>list= bookService.findBook(searchRequest.getSearchKey(),searchRequest.getSearchValue());
        return list;
    }
}
