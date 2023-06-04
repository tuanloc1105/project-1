package vn.com.tdtu.auth.service;

import vn.com.tdtu.auth.dto.RoleDto;
import vn.com.tdtu.auth.entity.Role;

import java.util.List;

public interface RoleService {
    List<RoleDto> findRoleByListName(List<String> roleName);

    List<Role> findRoleByList(List<String> roleName);
}
