package itz.next_step.android.data.model.api.request.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username = "quanbui";
    private String password = "123456";
    private String posId;
}

