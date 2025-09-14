package com.example.CraftYourStyle2.services;
import com.example.CraftYourStyle2.model.User;
import com.example.CraftYourStyle2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UserServices {
    private final UserRepository userRepository;

    @Autowired
    public UserServices(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUser(){
        return this.userRepository.findAll();
    }

}
