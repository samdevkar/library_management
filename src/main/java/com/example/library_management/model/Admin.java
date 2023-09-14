package com.example.library_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.atn.Transition;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String name;

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "admin")
    private List<Transaction> transactionList;

}
