package vn.com.tdtu.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.tdtu.user.dto.UserInfoDto;
import vn.com.tdtu.user.dto.UserInfoGrpcDto;
import vn.com.tdtu.user.entity.UserInfo;
import vn.com.tdtu.user.entity.repository.UserInfoRepo;
import vn.com.tdtu.user.mapper.UserInfoMapper;
import vn.com.tdtu.user.message.request.CreateUserInfoRequest;
import vn.com.tdtu.user.message.request.GetAllUserRequest;
import vn.com.tdtu.user.service.UserService;
import vn.com.tdtu.user.service.grpc.client.AuthServiceGrpcClient;
import vn.com.tdtu.common.exceptions.ErrorCodeEnum;
import vn.com.tdtu.common.exceptions.InternalServiceException;
import vn.com.tdtu.common.object.PageResponse;
import vn.com.tdtu.common.object.ResponseType;
import vn.com.tdtu.common.pagable.PageLink;
import vn.com.tdtu.common.service.BaseService;
import vn.com.tdtu.common.utils.DateTimeUtil;
import vn.com.tdtu.common.utils.StringUtil;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional(rollbackFor = {Exception.class, Throwable.class, RuntimeException.class})
public class UserServiceImpl extends BaseService<UserInfo, UserInfoRepo> implements UserService {

    private final AuthServiceGrpcClient authServiceGrpcClient;
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserServiceImpl(UserInfoRepo repository,
                           AuthServiceGrpcClient authServiceGrpcClient,
                           UserInfoMapper userInfoMapper) {
        super(repository);
        this.authServiceGrpcClient = authServiceGrpcClient;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public void createUser(CreateUserInfoRequest request) {
        LocalDate currentDate = DateTimeUtil.generateCurrentLocalDateDefault();
        UserInfoGrpcDto grpcDto = this.authServiceGrpcClient.create(request);
        var userInfo = new UserInfo();
        userInfo.setEmail(request.getEmail());
        userInfo.setAddress(request.getAddress());
        userInfo.setFullName(request.getFullName());
        Integer numberUserCreatedInDay = this.repository.countByCreatedAtBetween(
                currentDate.atTime(LocalTime.MIN),
                currentDate.atTime(LocalTime.MAX)
        );
        if (numberUserCreatedInDay > 999) {
            throw new InternalServiceException(ErrorCodeEnum.FAILURE.getCode(), "Max user create in a day");
        }
        //Long cif = (Long.parseLong(currentDate.format(DateTimeFormatter.ofPattern("yyMMdd"))) * 1000) + numberUserCreatedInDay;
        //userInfo.setCif(cif);
        userInfo.setCif(StringUtil.addZeroLeadingNumber(numberUserCreatedInDay == 0 ? numberUserCreatedInDay + 1 : numberUserCreatedInDay, "C"));
        userInfo.setUserUid(grpcDto.getUserUid());
        this.repository.save(userInfo);
    }

    @Override
    public PageResponse<UserInfoDto> getAllUser(GetAllUserRequest request) {
        PageLink pageLink = new PageLink(request.getPageSize(), request.getPageNumber());
        Page<UserInfo> page = this.repository.findAll(pageLink.toPageable());
        return PageResponse.create(page, userInfoMapper::userInfoDtoMapper);
    }

    @Override
    public ResponseType<?> deleteUser(Long id) {
        this.changeUserStatus(id, false);
        return ResponseType.ok(null);
    }

    @Override
    public ResponseType<?> activeUser(Long id) {
        this.changeUserStatus(id, true);
        return ResponseType.ok(null);
    }

    private void changeUserStatus(Long id, boolean status) {
        UserInfo userInfo = this.repository.findById(id).orElseThrow(() -> new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "User not exist"));
        userInfo.setActive(true);
        this.authServiceGrpcClient.changeUserStatus(userInfo.getUserUid(), status);
        this.save(userInfo);
    }
}
