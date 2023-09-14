package com.example.library_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "book_author")
    private List<Book>bookList;

}
