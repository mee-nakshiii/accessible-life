package com.accessiblelife.repository;

import com.accessiblelife.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // Updated method name to match entity field
    Optional<User> findByEmailAndPasswordHash(String email, String passwordHash);
}

