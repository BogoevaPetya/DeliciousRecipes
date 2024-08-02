package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeFullInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc

public class RecipeControllerIT {
    @MockBean
    private RecipeService recipeServiceMock;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    public void viewAddRecipeForm() throws Exception {
        mockMvc.perform(get("/recipes/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-recipe"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testAddRecipe_notSuccessful() throws Exception {
        mockMvc.perform(post("/recipes/add")
                .formField("name", "Cake")
                .formField("ingredients", "test ingredients, test ingredients")
                .formField("timeForCooking", "10")
                .formField("instructions", "test")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes/add"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteRecipe() throws Exception {
        Mockito.doNothing().when(recipeServiceMock).deleteRecipe(1L);

        mockMvc.perform(delete("/recipes/delete/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(recipeServiceMock).deleteRecipe(1L);
    }

    @Test
    @WithMockUser(username = "user")
    public void testViewRecipeByID() throws Exception {
        RecipeFullInfoDTO recipeFullInfoDTO = new RecipeFullInfoDTO();
        when(recipeServiceMock.getRecipeById(1L))
                .thenReturn(recipeFullInfoDTO);

        mockMvc.perform(get("/recipes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-info"))
                .andExpect(model().attributeExists("recipeInfo"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testViewAllSalads_Success() throws Exception {
        List<RecipeShortInfoDTO> allSalads = new ArrayList<>();

        when(recipeServiceMock.getAllRecipesByCategory(CategoryName.SALAD))
                .thenReturn(allSalads);

        mockMvc.perform(get("/recipes/salads"))
                .andExpect(status().isOk())
                .andExpect(view().name("salads"))
                .andExpect(model().attributeExists("allSalads"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testViewAllSoups_Success() throws Exception {
        List<RecipeShortInfoDTO> allSoups = new ArrayList<>();

        when(recipeServiceMock.getAllRecipesByCategory(CategoryName.SALAD))
                .thenReturn(allSoups);

        mockMvc.perform(get("/recipes/soups"))
                .andExpect(status().isOk())
                .andExpect(view().name("soups"))
                .andExpect(model().attributeExists("allSoups"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testViewAllMainDishes_Success() throws Exception {
        List<RecipeShortInfoDTO> allSoups = new ArrayList<>();

        when(recipeServiceMock.getAllRecipesByCategory(CategoryName.SALAD))
                .thenReturn(allSoups);

        mockMvc.perform(get("/recipes/main-dishes"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-dishes"))
                .andExpect(model().attributeExists("allMainDishes"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testViewAllDesserts_Success() throws Exception {
        List<RecipeShortInfoDTO> allDesserts = new ArrayList<>();

        when(recipeServiceMock.getAllRecipesByCategory(CategoryName.SALAD))
                .thenReturn(allDesserts);

        mockMvc.perform(get("/recipes/desserts"))
                .andExpect(status().isOk())
                .andExpect(view().name("desserts"))
                .andExpect(model().attributeExists("allDesserts"));
    }


}
