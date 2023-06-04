package vn.com.tdtu.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.tdtu.auth.dto.UserDto;
import vn.com.tdtu.auth.entity.Role;
import vn.com.tdtu.auth.entity.User;
import vn.com.tdtu.auth.entity.UserRole;
import vn.com.tdtu.auth.entity.repository.UserRepo;
import vn.com.tdtu.auth.mapper.UserMapper;
import vn.com.tdtu.auth.message.request.CreateUserRequest;
import vn.com.tdtu.auth.message.response.LoginResponse;
import vn.com.tdtu.auth.service.AuthService;
import vn.com.tdtu.auth.service.JwtService;
import vn.com.tdtu.auth.service.RoleService;
import vn.com.tdtu.auth.service.UserRoleService;
import vn.com.tdtu.common.exceptions.ErrorCodeEnum;
import vn.com.tdtu.common.exceptions.InternalServiceException;
import vn.com.tdtu.common.service.BaseService;
import vn.com.tdtu.common.utils.AesUtil;
import vn.com.tdtu.common.utils.BCryptUtil;
import vn.com.tdtu.common.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {Exception.class, Throwable.class, RuntimeException.class})
public class AuthServiceImpl extends BaseService<User, UserRepo> implements AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @Value("${aes_secret_key}")
    private String aesKey;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo,
                           UserMapper userMapper,
                           JwtService jwtService,
                           RoleService roleService, UserRoleService userRoleService) {
        super(userRepo);
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @Override
    public LoginResponse login(String username, String password) {
        User user = this.repository.findByUsernameAndActiveIsTrue(username)
                .orElseThrow(() -> new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "User not exists"));
        var dto = this.userMapper.userDtoMapper(user);
        var passHash = user.getPassword();
        BCryptUtil.comparePassword(password, passHash);
        return new LoginResponse(this.jwtService.generateJWTToken(dto));
    }

    @Override
    public UserDto authorize(String token) {
        var dto = this.jwtService.validateJwtToken(token);
        if (Optional.ofNullable(dto).isEmpty()) {
            throw new InternalServiceException(ErrorCodeEnum.AUTH_FAILURE.getCode(), "Authentication is required");
        }
        this.repository.findByUsernameAndActiveIsTrue(dto.getUsername())
                .orElseThrow(() -> new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "User not exists"));
        return dto;
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        var userUid = UUID.randomUUID();
        String username, password;
        try {
            username = AesUtil.decrypt(request.getEncryptUsername(), aesKey);
            password = AesUtil.decrypt(request.getEncryptPassword(), aesKey);
        } catch (Exception e) {
            throw new InternalServiceException(ErrorCodeEnum.CANNOT_DECRYPT.getCode(), e.getMessage());
        }
        boolean isExisted = this.repository.existsByUsername(username);
        if (isExisted) {
            throw new InternalServiceException(ErrorCodeEnum.EXISTS.getCode(), "User already existed");
        }
        var roles = this.roleService.findRoleByList(request.getRoles());
        if (roles.size() != request.getRoles().size()) {
            throw new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "Role input is not valid");
        }
        var user = User.builder()
                .userUid(userUid.toString())
                .active(true)
                .createdAt(DateTimeUtil.generateCurrentTimeDefault())
                .updatedAt(DateTimeUtil.generateCurrentTimeDefault())
                .username(username)
                .password(BCryptUtil.hashPassword(password))
                .build();
        user = this.save(user);
        var userRoleList = new ArrayList<UserRole>();
        for (Role role : roles) {
            var userRole = new UserRole(user, role);
            this.userRoleService.saveNew(userRole);
            userRoleList.add(userRole);
        }
        user.setUserRoles(userRoleList);
        return this.userMapper.userDtoMapper(user);
    }

    @Override
    public void deleteUser(String userUid) {
        var user = this.repository.findByUserUid(userUid)
                .orElseThrow(() -> new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "User not exists"));
        user.setActive(false);
        this.repository.save(user);
    }

    @Override
    public void activeUser(String userUid) {
        var user = this.repository.findByUserUid(userUid)
                .orElseThrow(() -> new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "User not exists"));
        user.setActive(true);
        this.repository.save(user);
    }

}
