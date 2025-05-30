package itz.next_step.android.data.model.api.request.candidate;

import lombok.Data;

@Data
public class CandidateUpdateProfileRequest {
    private String coverLetter;
    private String fullName;
    private Boolean isAutoApply;
    private Boolean isJobSearching;
    private String jobTitle;
    private String oldPassword;
    private String password;
}
