package com.user_api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "user_orders")
public class UserOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Long volume;
    private Double price;
    private Integer type;
    private Integer status;
    private Long remaining_value;

    private Timestamp created_on;
    private Timestamp updated_on;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public UserOrders() {
    }

    public UserOrders(User user, Long id, Long id_stock, String stock_symbol, String stock_name, Long volume,
            Double price, Integer type, Integer status, Long remaining_value) {
        this.user = user;
        this.id = id;
        this.id_stock = id_stock;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.volume = volume;
        this.price = price;
        this.type = type;
        this.status = status;
        this.remaining_value = remaining_value;
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserOrders that = (UserOrders) o;
        return id.equals(that.id) && id_stock.equals(that.id_stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_stock);
    }

}