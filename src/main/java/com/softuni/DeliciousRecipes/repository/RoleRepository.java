package com.softuni.DeliciousRecipes.repository;

import com.softuni.DeliciousRecipes.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
