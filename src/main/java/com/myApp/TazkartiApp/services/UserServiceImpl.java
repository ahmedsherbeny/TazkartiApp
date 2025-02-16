package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.UserCreateDTO;
import com.myApp.TazkartiApp.DTO.UserDTO;
import com.myApp.TazkartiApp.model.User;
import com.myApp.TazkartiApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserDTO(user);
    }

    @Override
    public UserCreateDTO createUser(UserCreateDTO userDTO) {
        User user = mapToEntity(userDTO);
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserCreateDTO updateUser(Long id, UserCreateDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // تحويل `User` إلى `UserDTO`
    private UserCreateDTO mapToDTO(User user) {
        return new UserCreateDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }
    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    // تحويل `UserDTO` إلى `User`
    private User mapToEntity(UserCreateDTO userDTO) {
        return new User(null, userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
    }
}
