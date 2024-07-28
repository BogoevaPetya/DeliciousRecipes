package com.softuni.DeliciousRecipes.repository;

import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserLikedRecipe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikedRecipeRepository extends JpaRepository<UserLikedRecipe, Long> {

    @Transactional
    void deleteByRecipe(Recipe recipe);

}
