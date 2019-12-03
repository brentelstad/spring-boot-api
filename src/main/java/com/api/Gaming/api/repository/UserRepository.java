package com.api.Gaming.api.repository;

import com.api.Gaming.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
