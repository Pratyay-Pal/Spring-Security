package com.spring.security.jpa;

import com.spring.security.entity.custom_users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface custom_users_jpa extends JpaRepository<custom_users, Integer> {

    List<custom_users> findByEmail(String email);

}
