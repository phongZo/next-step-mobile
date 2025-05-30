package itz.next_step.android.data.model.api.request.user;

import lombok.Data;

@Data
public class LoginRequest {
    String password;
    String phone;
}
