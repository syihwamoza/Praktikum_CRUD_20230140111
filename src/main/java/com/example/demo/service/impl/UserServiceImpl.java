package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserAddRequest;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationUtil validationUtil;

    @Override
    public UserDto addUser(UserAddRequest request) {
        validationUtil.validate(request);

        User saveUser = User.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .age(request.getAge())
                .build();

        userRepository.save(saveUser);

        UserDto userDto = UserMapper.MAPPER.toUserDtoData(saveUser);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDto = new ArrayList<>();
        for (User user : users){
            userDto.add(UserMapper.MAPPER.toUserDtoData(user));
        }
        return userDto;
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        return UserMapper.MAPPER.toUserDtoData(user);
    }

    @Override
    public UserDto updateUser(String id, UserAddRequest request) {
        validationUtil.validate(request);

        User existingUser = userRepository.findById(id).orElseThrow(() ->new RuntimeException("user not found"));
        User user = User.builder()
                .id(existingUser.getId())
                .name(request.getName())
                .age(request.getAge())
                .build();
        userRepository.save(user);

        return UserMapper.MAPPER.toUserDtoData(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
        userRepository.delete(user);

    }
}