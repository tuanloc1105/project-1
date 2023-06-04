package vn.com.tdtu.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.com.tdtu.auth.dto.UserDto;
import vn.com.tdtu.auth.message.request.LoginRequest;
import vn.com.tdtu.auth.message.response.LoginResponse;
import vn.com.tdtu.auth.service.AuthService;
import vn.com.tdtu.common.object.RequestType;
import vn.com.tdtu.common.object.ResponseType;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseType<LoginResponse> login(@RequestBody RequestType<LoginRequest> request) {
        return ResponseType.ok(this.authService.login(request.getRequest().getUsername(), request.getRequest().getPassword()));
    }

    @PostMapping("/retrieve-info")
    public ResponseType<UserDto> retrieveInfo(@RequestHeader("Authorization") String token) {
        return ResponseType.ok(this.authService.authorize(token));
    }

}
