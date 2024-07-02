package com.softuni.DeliciousRecipes.model.dto;

import jakarta.validation.constraints.*;

public class UserRegisterDTO {
    @NotNull
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotEmpty
    @Size(min = 3, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
