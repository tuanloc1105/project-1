package vn.com.tdtu.auth.entity.repository;

import vn.com.tdtu.auth.entity.Role;
import vn.com.tdtu.common.entity.repository.BaseRepository;

import java.util.List;

public interface RoleRepo extends BaseRepository<Role> {

    List<Role> findByRoleNameIn(List<String> roleName);

}
