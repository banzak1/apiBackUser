package com.user_api.service;

import com.user_api.model.UserStockBalances;
import com.user_api.repository.UserStockBalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStockService {
    @Autowired
    private UserStockBalancesRepository userStockBalancesRepository;

    public UserStockBalances salvar(UserStockBalances userStockBalances) {
        return userStockBalancesRepository.save(userStockBalances);
    }
}
