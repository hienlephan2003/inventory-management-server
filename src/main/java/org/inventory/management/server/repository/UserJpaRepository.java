package org.inventory.management.server.repository;

import org.inventory.management.server.entity.Product;
import org.inventory.management.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);
    Optional<User> findUserByUsername(String username);
}
