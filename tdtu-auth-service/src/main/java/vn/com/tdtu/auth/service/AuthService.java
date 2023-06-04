package vn.com.tdtu.auth.service;

import vn.com.tdtu.auth.dto.UserDto;
import vn.com.tdtu.auth.message.request.CreateUserRequest;
import vn.com.tdtu.auth.message.response.LoginResponse;

import java.util.UUID;

public interface AuthService {
    LoginResponse login(String username, String password);

    UserDto authorize(String token);

    UserDto createUser(CreateUserRequest request);

    void deleteUser(String userUid);

    void activeUser(String userUid);
}
