package com.user_api.controller;

import com.user_api.dto.StockDto;
import com.user_api.dto.UserOrdersDto;
import com.user_api.model.User;
import com.user_api.model.UserOrders;
import com.user_api.model.UserStockBalances;
import com.user_api.repository.BuyRepository;
import com.user_api.repository.UserOrdersRepository;
import com.user_api.repository.UsersRepository;
import com.user_api.service.StockService;
import com.user_api.service.UserOrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.*;
import java.util.List;

@CrossOrigin
@RestController
public class UserOrdersController {

    @Autowired
    private UserOrdersRepository userOrdersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private WebClient webClient;
    @Autowired
    private StockService stockService;
    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("/orders")
    public List<UserOrders> listar() {
        return userOrdersRepository.findAll();
    }

    @GetMapping("/uo/{id_user}")
    public ResponseEntity<List<UserOrders>> getUser(@PathVariable("id_user") Long id_user) {
        try {
            return ResponseEntity.ok().body(userOrderService.getUser(id_user));
        } catch (Exception e) {
            if (e.getMessage().equals("FAZENDA_NOT_FOUND"))
                return ResponseEntity.notFound().build();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<UserOrders> salvar(@RequestBody UserOrdersDto dto,
            @RequestHeader("Authorization") String token) {
        User user = usersRepository.findById(dto.getId_user()).orElseThrow();
        Double dollar = user.getDollar_balance();
        Double mult = dto.getPrice() * dto.getVolume();
        if (dollar >= mult) {// verifica se o usuario tem dinheiro na carteira pra criar uma ordem de compra
            UserOrders userOrders = userOrdersRepository.save(dto.tranformaParaObjeto1(user));
            stockService.teste1(userOrders.getId_stock(), token);
            userOrderService.vender();
            return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
        } else {
            System.out.println("Ordem não criada, valor insuficiente");
        }
        return null;
    }

    @PostMapping("/teste/{id}")
    public ResponseEntity<StockDto> teste(@PathVariable Long id, @RequestHeader("Authorization") String token)
            throws Exception {
        StockDto stockDto1 = this.stockService.teste1(id, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockDto1);
    }

}
