package com.example.library_management.controller;

import com.example.library_management.dto.InitiateTansactionRequest;
import com.example.library_management.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/transaction")
    public void intiateTransaction(@RequestBody @Valid InitiateTansactionRequest initiateTansactionRequest) throws Exception{
        transactionService.initiateTransaction(initiateTansactionRequest);
    }

}
