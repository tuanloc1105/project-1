package vn.com.tdtu.user.mapper;

import org.mapstruct.Mapper;
import vn.com.tdtu.user.dto.UserInfoDto;
import vn.com.tdtu.user.entity.UserInfo;

@Mapper(
        componentModel = "spring",
        imports = {
        }
)
public interface UserInfoMapper {

    UserInfoDto userInfoDtoMapper(UserInfo userInfo);

}
