package com.user_api.dto;

import com.user_api.model.UserOrders;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TesteDto {
    private Long id;
    private Integer status;

    public UserOrders tranformaParaObjeto2(Long id) {
        return new UserOrders(id, status);
    }
}
