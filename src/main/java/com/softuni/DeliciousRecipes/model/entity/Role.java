package com.softuni.DeliciousRecipes.model.entity;

import com.softuni.DeliciousRecipes.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private UserRole name;

    public Role() {
    }

    public UserRole getName() {
        return name;
    }

    public void setName(UserRole name) {
        this.name = name;
    }
}
