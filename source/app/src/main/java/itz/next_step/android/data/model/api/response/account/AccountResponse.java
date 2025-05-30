package itz.next_step.android.data.model.api.response.account;

import java.time.LocalDateTime;

import itz.next_step.android.data.model.api.response.group.GroupResponse;
import lombok.Data;

@Data
public class AccountResponse {
    private Integer attemptCode;
    private Integer attemptLogin;
    private String avatarPath;
    private String createdBy;
    private LocalDateTime createdDate;
    private String avatar;
    private String email;
    private String fullName;
    private GroupResponse group;
    private Long id;
    private Boolean isSuperAdmin;
    private Integer kind;
    private LocalDateTime lastLogin;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private String phone;
    private String resetPwdCode;
    private LocalDateTime resetPwdTime;
    private Long reusedId;
    private Integer status;
    private String username;
}
