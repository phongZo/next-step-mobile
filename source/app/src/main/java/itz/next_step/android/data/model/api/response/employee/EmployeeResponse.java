package itz.next_step.android.data.model.api.response.employee;

import itz.next_step.android.data.model.api.response.account.AccountResponse;
import lombok.Data;

@Data
public class EmployeeResponse {
    private AccountResponse account;
    private String code;
    private Long id;
    private String name;
}
