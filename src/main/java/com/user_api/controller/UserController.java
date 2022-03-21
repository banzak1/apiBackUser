package com.user_api.controller;

import com.user_api.dto.UserOrdersDto;
import com.user_api.model.User;
import com.user_api.model.UserOrders;
import com.user_api.model.UserStockBalances;
import com.user_api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users")
    public List<User> listar() {
        return usersRepository.findAll();
    }

    @GetMapping("/u/{username}")
    public Long lista(@PathVariable("username") String username) {
        Optional<User> us = usersRepository.findByUser(username);
        if (us.isEmpty()) {
            return usersRepository.save(new User(username, "teste1234", 10000.00)).getId();
        } else {
            return us.get().getId();
        }
    }

    @GetMapping("/user/{id}")
    public Double lista1(@PathVariable("id") Long id) {
        Optional<User> us = usersRepository.findById(id);
        if (us.isPresent()) {
            return us.get().getDollarBalance();
        }
        return null;
    }

    @PostMapping("/users")
    public User adicionar(@RequestBody UserDto user) {
        return usersRepository.save(user.tranformaParaObjeto1());

    }

}
