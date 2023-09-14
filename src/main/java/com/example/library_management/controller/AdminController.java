package com.example.library_management.controller;

import com.example.library_management.dto.CreateAdminRequest;
import com.example.library_management.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/createadmin")
    public void createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest){
        adminService.createAdmin(createAdminRequest.toAdmin());
    }



}
