package itz.next_step.android.data.model.api.response.candidate;

import itz.next_step.android.data.model.api.response.account.AccountResponse;
import lombok.Data;

@Data
public class CandidateResponse {
    private AccountResponse account;
    private String coverLetter;
    private Long id;
    private Boolean isAutoApply;
    private Boolean isJobSearching;
    private String jobTitle;
}
