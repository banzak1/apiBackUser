package com.user_api.dto;

import com.user_api.model.User;
import com.user_api.model.UserOrders;
import com.user_api.model.UserStockBalance;
import com.user_api.model.UserStockBalances;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOrdersDto {
    private Long id;
    private Long idUser;
    private Long idStock;
    private String stockSymbol;
    private String stockName;
    private Long volume;
    private Double price;
    private Integer type;
    private Integer status;
    private Long remainingValue;

    public UserOrders tranformaParaObjeto1(User user) {
        UserOrders uo = new UserOrders();
        uo.setUser(user);
        uo.setIdStock(idStock);
        uo.setStockName(stockName);
        uo.setStockSymbol(stockSymbol);
        uo.setVolume(volume);
        uo.setPrice(price);
        uo.setType(type);
        uo.setStatus(status);
        uo.setRemainingValue(remainingValue);
        return uo;
    }
}
