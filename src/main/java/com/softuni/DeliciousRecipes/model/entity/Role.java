package com.softuni.DeliciousRecipes.model.entity;

import com.softuni.DeliciousRecipes.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Role() {
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
