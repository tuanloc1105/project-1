package vn.com.tdtu.auth.entity.repository;

import vn.com.tdtu.auth.entity.User;
import vn.com.tdtu.common.entity.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends BaseRepository<User> {

    Optional<User> findByUsernameAndActiveIsTrue(String username);
    Optional<User> findByUserUidAndActiveIsTrue(String userUid);
    Optional<User> findByUserUid(String userUid);

    boolean existsByUsername(String username);

}
