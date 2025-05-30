package itz.next_step.android.data.model.api.request.company;

import lombok.Data;

@Data
public class CreateCompanyRequest {
    private String banner;
    private String description;
    private String hotline;
    private String logo;
    private String name;
    private String shortDescription;
}
