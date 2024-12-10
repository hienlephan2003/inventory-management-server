package org.inventory.management.server.repository;

import org.inventory.management.server.entity.Product;
import org.inventory.management.server.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Tag> findByIsDeletedFalse();
}
