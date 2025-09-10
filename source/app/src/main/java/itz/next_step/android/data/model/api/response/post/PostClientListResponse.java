package itz.next_step.android.data.model.api.response.post;

import java.util.List;

import lombok.Data;

@Data
public class PostClientListResponse<CompanyResponse> {
    private CompanyResponse company;
    private int contractType;
    private String description;
    private int experience;
    private String expireDate;
    private Long id;
    private String level;
    private int maxSalary;
    private int minSalary;
    private String name;
    private String tag;
    private int totalSlot;
    private int type;
}
