package vn.com.tdtu.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.tdtu.auth.entity.UserRole;
import vn.com.tdtu.auth.entity.repository.UserRoleRepo;
import vn.com.tdtu.auth.service.UserRoleService;
import vn.com.tdtu.common.service.BaseService;

@Service
@Transactional(rollbackFor = {Exception.class, Throwable.class, RuntimeException.class})
public class UserRoleServiceImpl extends BaseService<UserRole, UserRoleRepo> implements UserRoleService {

    @Autowired
    public UserRoleServiceImpl(UserRoleRepo repository) {
        super(repository);
    }

    @Override
    public void saveNew(UserRole userRole) {
        this.save(userRole);
    }

}
