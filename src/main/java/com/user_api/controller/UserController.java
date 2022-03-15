package com.user_api.controller;

//import com.user_api.model.Stock;
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

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private WebClient webClient;

    @GetMapping("/users")
    public List<User> listar() {
        return usersRepository.findAll();
    }

    @PostMapping("/users")
    public User adicionar(@RequestBody User user) {
        return usersRepository.save(user);

    }

}
