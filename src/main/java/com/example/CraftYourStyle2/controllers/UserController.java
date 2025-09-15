package com.example.CraftYourStyle2.controllers;
import com.example.CraftYourStyle2.dto.LoginUserDto;
import com.example.CraftYourStyle2.dto.RegisterUserDto;
import com.example.CraftYourStyle2.model.User;
import com.example.CraftYourStyle2.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PostMapping
    public ResponseEntity<Object> registrarUsuario(@Valid @RequestBody RegisterUserDto dto){
        return this.userServices.crearUsuario(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUsuario(@Valid @RequestBody LoginUserDto dto){
        return this.userServices.login(dto);
    }

}
