package com.user_api.repository;

import com.user_api.model.User;
import com.user_api.model.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * from users where id= 1", nativeQuery = true)
    List<UserOrders> findByUser();
}
