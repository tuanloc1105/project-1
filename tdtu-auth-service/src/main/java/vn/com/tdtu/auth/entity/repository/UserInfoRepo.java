package vn.com.tdtu.auth.entity.repository;

import vn.com.tdtu.auth.entity.UserInfo;
import vn.com.tdtu.common.entity.repository.BaseRepository;

import java.time.LocalDateTime;

public interface UserInfoRepo extends BaseRepository<UserInfo> {

    Integer countByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
