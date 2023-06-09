package vn.com.tdtu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.common.dto.BaseEntityDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto extends BaseEntityDto {
    private String userUid;
    private String cif;
    private String email;
    private String address;
    private String fullName;
}
