package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private UserEntity map(UserRegisterDTO userRegisterDTO) {
        UserEntity mappedEntity = modelMapper.map(userRegisterDTO, UserEntity.class);
        mappedEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        return mappedEntity;
    }
}
