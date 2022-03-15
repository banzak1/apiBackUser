package com.user_api.service;

import com.user_api.dto.UserOrdersDto;
import com.user_api.model.UserOrders;
import com.user_api.model.UserStockBalances;
import com.user_api.repository.BuyRepository;
import com.user_api.repository.UserOrdersRepository;
import com.user_api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserOrderService {
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

    public UserOrders vender() {
        List<UserOrders> teste1 = userOrdersRepository.testando1();
        List<UserOrders> userteste = buyRepository.fyndteste();
        List<UserOrders> userteste1 = buyRepository.findtTeste1();
        List<UserOrders> userFind = userOrdersRepository.findByCalculo();
        if (!userFind.isEmpty()) {
            System.out.println("venda positiva");
            for (UserOrders cont : userFind) {
                userOrdersRepository.updateDollarBalance(cont.getUser());
                userOrdersRepository.updateRemainingValue(cont);
                userOrdersRepository.atualizarBalance(cont.getUser(), cont.getId_stock());
            }
        }
        if (!userteste1.isEmpty()) {
            System.out.println("compra negativa");
            for (UserOrders cont : userteste1) {
                buyRepository.updateDollarBalanceNE(cont, cont.getUser());
                buyRepository.teste1(cont.getUser(), cont.getId_stock(), cont.getStock_symbol(),
                        cont.getStock_name());
                buyRepository.atualizarBalanceNE(cont.getId(), cont.getUser(), cont.getId_stock());
                buyRepository.RemainingNE(cont);
            }
        }
        if (!userteste.isEmpty()) {
            System.out.println("compra positiva");
            for (UserOrders cont : userteste) {
                // compraRepository.teste1(cont.getUser(), cont.getId_stock(),
                // cont.getStock_symbol(), cont.getStock_name());
                buyRepository.updateDollarBalancePO(cont.getUser(), cont);
                buyRepository.RemainigPO(cont, cont.getUser());
                buyRepository.teste1(cont.getUser(), cont.getId_stock(), cont.getStock_symbol(),
                        cont.getStock_name());
                buyRepository.atualizarBalancePO(cont.getId(), cont.getUser(), cont.getId_stock());
            }
            // userOrdersRepository.updateStatus();
        }
        if (!teste1.isEmpty()) {
            System.out.println("venda negativa");
            for (UserOrders cont : teste1) {
                userOrdersRepository.RemainingNE(cont.getUser(), cont.getId_stock());
                userOrdersRepository.updateDollarBalanceNE(cont, cont.getUser());
                userOrdersRepository.updateRemainingValue2(cont);
            }
        }
        userOrdersRepository.updateStatus2();
        return null;
    }

    public List<UserOrders> getUser(Long id_user) throws Exception {
        List<UserOrders> userOrders = userOrdersRepository.listOrders(id_user);
        return userOrders;
    }
}
