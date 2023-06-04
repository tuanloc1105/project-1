package vn.com.tdtu.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.com.tdtu.auth.entity.Role;
import vn.com.tdtu.auth.entity.User;
import vn.com.tdtu.auth.entity.UserInfo;
import vn.com.tdtu.auth.entity.UserRole;
import vn.com.tdtu.auth.entity.repository.RoleRepo;
import vn.com.tdtu.auth.entity.repository.UserInfoRepo;
import vn.com.tdtu.auth.entity.repository.UserRepo;
import vn.com.tdtu.auth.entity.repository.UserRoleRepo;
import vn.com.tdtu.common.pagable.PageLink;
import vn.com.tdtu.common.utils.BCryptUtil;
import vn.com.tdtu.common.utils.DateTimeUtil;
import vn.com.tdtu.common.utils.StringUtil;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserRoleRepo userRoleRepo;
    private final UserInfoRepo userInfoRepo;

    @Override
    public void run(String... args) throws Exception {
        /*
        List<Role> role = List.of(
                new Role("ADMIN", null),
                new Role("LSO", null),
                new Role("CUSTOMER", null)
        );
        this.roleRepo.saveAll(role);
        String userUid = UUID.randomUUID().toString();
        User user = User.builder()
                .userUid(userUid)
                .active(true)
                .createdAt(DateTimeUtil.generateCurrentTimeDefault())
                .updatedAt(DateTimeUtil.generateCurrentTimeDefault())
                .username("admin")
                .password(BCryptUtil.hashPassword("admin"))
                .build();
        PageLink pageLink = new PageLink(1, 0, null);
        Page<User> users = this.userRepo.findAll(pageLink.toPageable());
        if (users.isEmpty()) {
            this.userRepo.save(user);
        }
        Role role1 = Role.builder().id(1L).build();
        User user1 = User.builder().id(1L).build();
        this.userRoleRepo.save(new UserRole(user1, role1));

        UserInfo userInfo = new UserInfo();
        userInfo.setAddress("HCM");
        //String formatted = String.format("%06d", user.getId());
        //System.out.println(formatted);
        userInfo.setCif(StringUtil.addZeroLeadingNumber(user.getId().intValue(), "C"));
        userInfo.setUserUid(userUid);
        userInfo.setFullName("SYSTEM ADMIN");
        userInfo.setEmail("abc@mail.com");
        this.userInfoRepo.save(userInfo);
        */
    }
}
