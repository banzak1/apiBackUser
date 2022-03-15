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
    private Long id_user;
    private Long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Long volume;
    private Double price;
    private Integer type;
    private Integer status;
    private Long remaining_value;

    public UserOrders tranformaParaObjeto1(User user) {
        return new UserOrders(user, id, id_stock, stock_symbol, stock_name, volume, price, type, status,
                remaining_value);
    }
}
