package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.FoodInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        boolean passwordMatch = userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());
        if (!passwordMatch){
            return false;
        }

        userRepository.save(map(userRegisterDTO));
        return true;
    }

    public UserInfoDTO getUserDetails(Long id){
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return null;
        }

        UserEntity user = optionalUser.get();

        List<String> stringRoles = user.getRoles().stream()
                .map(role -> role.getRole().name().toString()).toList();


        List<FoodInfoDTO> favorites = user.getFavoriteRecipes().stream().map(recipe -> {
            FoodInfoDTO dto = modelMapper.map(recipe, FoodInfoDTO.class);
            dto.setAddedBy(recipe.getAddedBy().getUsername());
            return dto;
        }).toList();

        List<FoodInfoDTO> addedByMe = user.getAddedRecipes().stream().map(recipe -> {
            FoodInfoDTO dto = modelMapper.map(recipe, FoodInfoDTO.class);
            return dto;
        }).toList();

        UserInfoDTO userInfoDTO = this.modelMapper.map(user, UserInfoDTO.class);
        userInfoDTO.setRoles(stringRoles);
        userInfoDTO.setFavorites(favorites);
        userInfoDTO.setAddedByMe(addedByMe);

        return userInfoDTO;
    }

    private UserEntity map(UserRegisterDTO userRegisterDTO) {
        UserEntity mappedEntity = modelMapper.map(userRegisterDTO, UserEntity.class);
        mappedEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        return mappedEntity;
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public Optional<UserEntity> findUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
