package vn.com.tdtu.user.entity.repository;

import vn.com.tdtu.user.entity.UserInfo;
import vn.com.tdtu.common.entity.repository.BaseRepository;

import java.time.LocalDateTime;

public interface UserInfoRepo extends BaseRepository<UserInfo> {

    Integer countByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
