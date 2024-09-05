package com.deepaksharma.shoppingmanagementsystem.repository;

import com.deepaksharma.shoppingmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
