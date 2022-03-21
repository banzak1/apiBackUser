package com.user_api.dto;

import com.user_api.model.User;
import com.user_api.model.UserStockBalance;
import com.user_api.model.UserStockBalances;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStockDto {
    private Long idUser;
    private Long idStock;
    private String stockSymbol;
    private String stockName;
    private Long volume;

    public UserStockBalances tranformaParaObjeto(User user) {
        return new UserStockBalances(new UserStockBalance(user, idStock), stockSymbol, stockName, volume);
    }

}
