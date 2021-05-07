package com.intern.repository;

import com.intern.entity.UserBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserBO,Long> {
    @Query("select  u from UserBO  u  where   u.username =  : username")
    Optional<UserBO> getUserByUsername(@Param("username") String username);
}
