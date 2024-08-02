package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.FoodInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.RoleRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import com.softuni.DeliciousRecipes.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.softuni.DeliciousRecipes.model.enums.UserRole.USER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
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
        userInfoDTO.setFavorites(favorites);
        userInfoDTO.setAddedByMe(addedByMe);

        return userInfoDTO;
    }

    public UserEntity map(UserRegisterDTO userRegisterDTO) {
        UserEntity mappedEntity = modelMapper.map(userRegisterDTO, UserEntity.class);
        mappedEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        mappedEntity.setRoles(List.of(roleRepository.findByRole(USER)));
        return mappedEntity;
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public UserEntity getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(IllegalArgumentException::new);
    }

    public String getLoggedUserUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
