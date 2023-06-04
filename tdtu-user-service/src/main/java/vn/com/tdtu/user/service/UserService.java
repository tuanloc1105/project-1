package vn.com.tdtu.user.service;

import vn.com.tdtu.user.dto.UserInfoDto;
import vn.com.tdtu.user.message.request.CreateUserInfoRequest;
import vn.com.tdtu.user.message.request.GetAllUserRequest;
import vn.com.tdtu.common.object.PageResponse;
import vn.com.tdtu.common.object.ResponseType;

public interface UserService {
    void createUser(CreateUserInfoRequest request);

    PageResponse<UserInfoDto> getAllUser(GetAllUserRequest request);

    ResponseType<?> deleteUser(Long id);

    ResponseType<?> activeUser(Long id);
}
