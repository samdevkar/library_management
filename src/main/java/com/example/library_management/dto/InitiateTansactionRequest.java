package com.example.library_management.dto;

import com.example.library_management.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitiateTansactionRequest {
    @NotNull
    private String rollNumber;
    @NotNull
    private Integer bookId;
    @NotNull
    private Integer adminId;
    @NotNull
    private TransactionType transactionType;
}
