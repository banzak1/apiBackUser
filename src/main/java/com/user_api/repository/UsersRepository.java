package com.user_api.repository;

import com.user_api.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * from users where username= ?1", nativeQuery = true)
    Optional<User> findByUser(String username);
}
