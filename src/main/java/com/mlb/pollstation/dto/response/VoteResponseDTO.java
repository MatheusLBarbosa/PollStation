package com.mlb.pollstation.dto.response;

import com.mlb.pollstation.enums.VoteChoice;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteResponseDTO {
    @NotNull
    private String cpf;
    @NotNull
    private VoteChoice voteChoice;
}
