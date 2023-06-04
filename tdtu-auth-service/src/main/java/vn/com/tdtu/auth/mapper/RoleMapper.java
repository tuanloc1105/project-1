package vn.com.tdtu.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.com.tdtu.auth.dto.RoleDto;
import vn.com.tdtu.auth.entity.Role;

@Mapper(
        componentModel = "spring",
        imports = {
        }
)
public interface RoleMapper {

    @Mapping(source = "roleName", target = "roleName")
    RoleDto roleDtoMapper(Role user);

}
