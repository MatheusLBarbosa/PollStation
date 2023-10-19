package com.mlb.pollstation.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
public class IssueResponseDTO {
    private String message;
    private Long issueId;
}
