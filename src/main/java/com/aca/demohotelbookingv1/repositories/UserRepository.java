package com.aca.demohotelbookingv1.repositories;

import com.aca.demohotelbookingv1.model.Role;
import com.aca.demohotelbookingv1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByRole(String role);
}
