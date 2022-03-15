package com.user_api.service;

import com.user_api.model.UserStockBalances;
import com.user_api.repository.UserStockBalancesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStockBalanceService {
    private final UserStockBalancesRepository repository;

    public List<UserStockBalances> getStock(Long id_user, String stock_name) throws Exception {
        List<UserStockBalances> userStockBalances = repository.listCarteira2(id_user, stock_name);
        return userStockBalances;
    }

    public List<UserStockBalances> getUser(Long id_user) throws Exception {
        List<UserStockBalances> userStockBalances = repository.listCarteira(id_user);
        return userStockBalances;
    }
}
