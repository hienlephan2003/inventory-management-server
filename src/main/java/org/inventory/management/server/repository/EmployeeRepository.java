package org.inventory.management.server.repository;

import org.inventory.management.server.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsUserByUsername(String username);
    Optional<Employee> findUserByUsername(String username);
}
