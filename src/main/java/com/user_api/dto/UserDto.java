package com.user_api.dto;

import com.user_api.model.User;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private Double dollarBalance;

    public User tranformaParaObjeto1() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDollarBalance(dollarBalance);
        return user;
    }
}
