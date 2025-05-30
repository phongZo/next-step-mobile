package itz.next_step.android.data.model.api.request.candidate;

import lombok.Data;

@Data
public class CandidateSignUpRequest {
    private String coverLetter;
    private String email;
    private String fullName;
    private Boolean isAutoApply;
    private Boolean isJobSearching;
    private String jobTitle;
    private String password;
    private String phone;
    private String username;
}
