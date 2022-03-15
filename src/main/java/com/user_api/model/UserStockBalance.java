package com.user_api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserStockBalance implements Serializable {

    private Long id_stock;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public UserStockBalance(User user, Long id_stock) {
        this.id_stock = id_stock;
        this.user = user;
    }

    public UserStockBalance() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserStockBalance that = (UserStockBalance) o;
        return Objects.equals(id_stock, that.id_stock) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_stock, user);
    }
}
