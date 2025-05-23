package itz.next_step.android.data.model.api.request.login;

import lombok.Data;

@Data
public class CandidateLoginRequest {
    private String phone;
    private String password;
    private String grant_type = "candidate";
}
