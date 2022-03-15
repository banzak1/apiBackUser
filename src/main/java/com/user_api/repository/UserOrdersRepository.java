package com.user_api.repository;

import com.user_api.model.User;
import com.user_api.model.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
        @Query(value = "SELECT * FROM user_orders WHERE type = 1 and id_stock = ?1 and status = 1", nativeQuery = true)
        List<UserOrders> findByTypeStock(Long id_stock);// procurar ordens de venda abertas

        @Query(value = " SELECT * FROM user_orders a, user_orders b " +
                        " where a.type <> b.type  and a.remaining_value >= b.remaining_value and a.type = 1  and a.id_stock = b.id_stock and a.status = b.status and a.status <>2 and b.status <> 2 and a.price <= b.price order by a.created_on asc ", nativeQuery = true)
        List<UserOrders> findByCalculo();// Pegando matches

        @Modifying
        @Query(value = "update user_orders  set remaining_value = (SELECT  a.remaining_value - b.remaining_value AS ID FROM user_orders a, user_orders b  WHERE a.type =1  and a.id_stock = b.id_stock and a.id <> b.id and b.remaining_value <> 0 and a.id = ?1 fetch first 1 rows only) where type =  1 AND id=?1", nativeQuery = true)
        int updateRemainingValue(UserOrders id);// Ele atualiza remaining value quando há match

        @Modifying
        @Query(value = "update user_orders  set remaining_value = 0 WHERE remaining_value < 0", nativeQuery = true)
        int updateStatus();

        @Modifying
        @Query(value = "UPDATE user_orders SET status = 2 WHERE remaining_value = 0", nativeQuery = true)
        int updateStatus2();

        @Modifying
        @Query(value = "update users set dollar_balance = ( select a.remaining_value * uo.price + u.dollar_balance " +
                        " FROM user_orders a, user_orders uo " +
                        " inner join users u on uo.id_user = u.id " +
                        " where uo.status = 1  and a.id_stock = uo.id_stock and uo.type = 1 and uo.id <> a.id and uo.id_user = ?1 fetch first 1 rows only) where id = ?1", nativeQuery = true)
        int updateDollarBalance(User user);

        @Modifying
        @Query(value = "update users set dollar_balance =  dollar_balance +( " +
                        "select a.remaining_value  * a.price " +
                        "fROM user_orders a, user_orders uo " +
                        "where  a.id_stock = uo.id_stock and a.type = 1  and uo.id <> a.id and a.id = ?1  and a.type <> uo.type "
                        +
                        ") where id = ?2", nativeQuery = true)
        int updateDollarBalanceNE(UserOrders id_order, User user);

        @Modifying
        @Query(value = "update user_stock_balances set volume = volume - ( select  uo.volume - uo.remaining_value " +
                        "  AS ID FROM user_orders a, user_orders uo " +
                        "  Inner join users u on id_user = u.id " +
                        "  inner join user_stock_balances usb on u.id = usb.id_user " +
                        "  WHERE  a.id_stock = usb.id_stock and a.id_user = ?1 and a.id_stock = ?2 and uo.type = 0 fetch first 1 rows only ) where id_user = ?1 and id_stock = ?2 ", nativeQuery = true)
        int atualizarBalance(User user, Long id_stock);

        @Query(value = "select * from" +
                        " user_orders a, user_orders b " +
                        " where a.remaining_value < b.remaining_value and  a.type = 1 and a.id_stock = b.id_stock and a.id <> b.id  and a.status <>2 and b.status <> 2 and a.type <> b.type and a.price <= b.price order by a.created_on asc", nativeQuery = true)
        List<UserOrders> testando1();

        @Modifying
        @Query(value = "update user_orders  set remaining_value = 0 where id=?1 and type = 1 ", nativeQuery = true)
        int updateRemainingValue2(UserOrders id_order);// Ele atualiza remaining value quando há match

        @Modifying
        @Query(value = "update user_stock_balances set volume = ( " +
                        " select  usb.volume - a.remaining_value " +
                        " AS ID FROM user_orders a " +
                        " Inner join users u on a.id_user = u.id " +
                        " inner join user_stock_balances usb on u.id = usb.id_user " +
                        "  WHERE a.id_user = ?1 and a.id_stock = ?2 and usb.id_stock = ?2" +
                        " ) where id_user = ?1 and id_stock = ?2", nativeQuery = true)
        int RemainingNE(User user, Long id_stock);

        @Query(value = "select MAX(price) from user_orders where id_stock = ?1 and status = 1 and type = 1", nativeQuery = true)
        Double getAskMax(Long id_stock);

        @Query(value = "select MIN(price) from user_orders where id_stock = ?1 and status = 1 and type = 1", nativeQuery = true)
        Double getAskMin(Long id_stock);

        @Query(value = "select MAX(price) from user_orders where id_stock = ?1 and status = 1 and type = 0 ", nativeQuery = true)
        Double getBidMax(Long id_stock);

        @Query(value = "select MIN(price) from user_orders where id_stock = ?1 and status = 1 and type = 0", nativeQuery = true)
        Double getBidMin(Long id_stock);

        @Query(value = "select * from user_orders uo where id_user=?1", nativeQuery = true)
        List<UserOrders> listOrders(Long id_user);

}