package com.example.demo.service;

import com.example.demo.model.dto.UserAddRequest;
import com.example.demo.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserAddRequest request);

    List<UserDto> getAllUser();

    UserDto getUserById(String id);

    UserDto updateUser(String id, UserAddRequest request);

    void deleteUser(String id);
}
