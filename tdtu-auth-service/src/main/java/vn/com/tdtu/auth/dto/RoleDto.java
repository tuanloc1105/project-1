package vn.com.tdtu.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.common.dto.BaseEntityDto;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoleDto extends BaseEntityDto {

    private String roleName;

}
