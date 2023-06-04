package vn.com.tdtu.auth.service;

import vn.com.tdtu.auth.dto.UserDto;

public interface JwtService {
    String generateJWTToken(UserDto userDto);

    UserDto validateJwtToken(String token);
}
