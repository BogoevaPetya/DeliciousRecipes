package com.softuni.DeliciousRecipes.init;

import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.enums.UserRole;
import com.softuni.DeliciousRecipes.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleInit implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.roleRepository.count() == 0){

            List<Role> roles = new ArrayList<>();

            Arrays.stream(UserRole.values())
                    .forEach(r -> {
                        Role role = new Role();
                        role.setRole(r);
                        roles.add(role);

                    });
            roleRepository.saveAll(roles);
        }
    }
}
