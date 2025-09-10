package itz.next_step.android.data.model.api;

import java.util.List;

import lombok.Data;

@Data
public class ResponseListObj<T> {
    private List<T> content;
    private int totalPages;
    private Long totalElements;
}
