package com.mlb.pollstation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueRequestDTO {
    @NotBlank(message = "Title may not be empty or null")
    private String title;
    @NotBlank(message = "Description may not be empty or null")
    private String description;
    @NotBlank(message = "Owner may not be empty or null")
    private String owner;
}
