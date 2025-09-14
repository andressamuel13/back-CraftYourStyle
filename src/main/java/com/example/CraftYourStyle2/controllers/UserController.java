package com.example.CraftYourStyle2.controllers;
import com.example.CraftYourStyle2.model.User;
import com.example.CraftYourStyle2.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "v1/usuarios")
public class UserController {
    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices){
        this.userServices = userServices;
    }

    @GetMapping
    public List<User> getUser(){
        return userServices.getUser();
    }


}
