package com.mlb.pollstation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueResponseDTO {
    private String message;
    private Long issueId;
}
