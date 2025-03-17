package com.example.management.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.management.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmail(String email);

    boolean existsByEmail(String email);
}
