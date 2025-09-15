package com.example.CraftYourStyle2.services;
import com.example.CraftYourStyle2.config.SecurityConfig;
import com.example.CraftYourStyle2.dto.LoginUserDto;
import com.example.CraftYourStyle2.dto.RegisterUserDto;
import com.example.CraftYourStyle2.model.User;
import com.example.CraftYourStyle2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServices(PasswordEncoder passwordEncoder ,UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<User> getUser(){
        return this.userRepository.findAll();
    }

    public ResponseEntity<Object> crearUsuario(RegisterUserDto dto){
        try {
            Optional<User> respuesta = userRepository.findByEmail(dto.getEmail());
            HashMap<String,Object> datos = new HashMap<>();

            if(respuesta.isPresent()) {
                datos.put("error",true);
                datos.put("message","error este email ya esta registrado");
                return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
            }

            User user = new User();
            user.setNombre(dto.getNombre());
            user.setEmail(dto.getEmail());
            user.setContraseña(dto.getContraseña());

            user.setContraseña(passwordEncoder.encode(dto.getContraseña()));
            User nuevoUsuario = userRepository.save(user);
            datos.put("Usuario",nuevoUsuario);
            datos.put("message","usuario creado");

            return new ResponseEntity<>(datos,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", "Ocurrió un error al crear el usuario");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> login(LoginUserDto dto){
        try{
            HashMap<String,Object> respuesta = new HashMap<>();
            Optional<User> datos = userRepository.findByEmail(dto.getEmail());

            if(datos.isEmpty()){
                respuesta.put("error",true);
                respuesta.put("message","usuario no encontrado");
                return new ResponseEntity<>(respuesta,HttpStatus.NOT_FOUND);
            }

            User user = datos.get();

            if (!passwordEncoder.matches(dto.getContraseña(), user.getContraseña())) {
                respuesta.put("error", true);
                respuesta.put("message", "Contraseña incorrecta");
                return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
            }

            user.setContraseña(null);

            respuesta.put("usuario", user.getEmail());
            respuesta.put("message", "Login exitoso");

            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", "Ocurrió un error al iniciar sesión");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
