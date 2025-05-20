package itz.next_step.android.data.model.api.request.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String avatarPath;
    private String email;

    private String fullName; // Bắt buộc (*)
    private Long id;         // Bắt buộc (*), kiểu Long tương ứng với int64
    private String password;
    private String phone;
}
