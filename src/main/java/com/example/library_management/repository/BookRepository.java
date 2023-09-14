package com.example.library_management.repository;

import com.example.library_management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query("Select b from Book b where b.name=:name")
    List<Book>findByName(String name);
    @Query("Select b from Book b ,Author a where b.book_author.id=a.id and a.name= ?1")
    List<Book>findByAuthorName(String author_name);

    @Query("Select b from Book b where b.genre=:genre")
    List<Book>findByGenre(String genre);
}
