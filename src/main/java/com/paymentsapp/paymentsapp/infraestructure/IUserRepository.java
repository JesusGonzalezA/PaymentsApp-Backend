package com.paymentsapp.paymentsapp.infraestructure;

import com.paymentsapp.paymentsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    @Query("FROM User WHERE username=:username")
    User findByUsername(@Param("username") String username);
}
