package com.user_api.controller;

import com.user_api.dto.StockDto;
import com.user_api.dto.UserOrdersDto;
import com.user_api.model.User;
import com.user_api.model.UserOrders;
import com.user_api.model.UserStockBalances;
import com.user_api.repository.UserOrdersRepository;
import com.user_api.repository.UserStockBalancesRepository;
import com.user_api.repository.UsersRepository;
import com.user_api.service.StockService;
import com.user_api.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserOrdersController {

    @Autowired
    private UserOrdersRepository userOrdersRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StockService stockService;
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private UserStockBalancesRepository usbRepository;

    @GetMapping("/orders")
    public List<UserOrders> listar() {
        return userOrdersRepository.findAll();
    }

    @GetMapping("/uo/{id_user}")
    public ResponseEntity<Page<UserOrders>> getUser(@PathVariable("id_user") Long idUser, @RequestParam int pageSize,
            @RequestParam int pageNumber) {
        try {
            return ResponseEntity.ok().body(userOrderService.getUser(idUser, pageNumber, pageSize));
        } catch (Exception e) {
            if (e.getMessage().equals("FAZENDA_NOT_FOUND"))
                return ResponseEntity.notFound().build();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<UserOrders> salvar(@RequestBody UserOrdersDto dto,
            @RequestHeader("Authorization") String token) {
        User user = usersRepository.findById(dto.getIdUser()).orElseThrow();
        List<UserStockBalances> verificar = usbRepository.verficarStock(dto.getIdUser(), dto.getIdStock());
        Double dollar = user.getDollarBalance();
        Double mult = dto.getPrice() * dto.getVolume();
        if (dollar >= mult && dto.getType() == 0) {// verifica se o usuario tem dinheiro na carteira pra criar uma ordem
                                                   // de compra
            UserOrders userOrders = userOrdersRepository.save(dto.tranformaParaObjeto1(user));
            stockService.teste1(userOrders.getIdStock(), token);
            userOrderService.dollarDisponivel(userOrders);
            userOrderService.match(userOrders);

            return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
        } else if (dto.getType() == 1 && !verificar.isEmpty()) {
            if (dto.getVolume() <= verificar.get(0).getVolume()) {
                UserOrders userOrders = userOrdersRepository.save(dto.tranformaParaObjeto1(user));
                stockService.teste1(userOrders.getIdStock(), token);
                userOrderService.volumeDisponivel(userOrders);
                userOrderService.match(userOrders);
                return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @PostMapping("/teste/{id}")
    public ResponseEntity<StockDto> teste(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        StockDto stockDto1 = this.stockService.teste1(id, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockDto1);
    }

    @PatchMapping("/alterar/{id}")
    public String alterar1(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Optional<UserOrders> userOrders = userOrdersRepository.findById(id);
        if (userOrders.isPresent()) {
            userOrders.get().getUser()
                    .setDollarBalance(userOrderService.fecharOrdemC(userOrders.get().getVolume(),
                            userOrders.get().getPrice(), userOrders.get().getUser().getDollarBalance(),
                            userOrders.get().getType(), userOrders.get().getRemainingValue()));
            userOrderService.fecharOrdemV(userOrders.get().getUser(), userOrders.get().getIdStock(),
                    userOrders.get().getType(), userOrders.get().getRemainingValue(), userOrders.get().getVolume());
            userOrderService.update(id);
            return "Ordem atualizada";
        } else {
            return null;
        }
    }

}
