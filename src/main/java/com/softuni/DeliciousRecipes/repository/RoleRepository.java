package com.softuni.DeliciousRecipes.repository;

import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(UserRole userRole);
}
