package com.softuni.DeliciousRecipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;



@Configuration
public class RestConfig {

    @Bean("recipesRestClient")
    public RestClient ordersRestClient(RecipeApiConfig recipeApiConfig){
        return RestClient
                .builder()
                .baseUrl(recipeApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
