package com.mlb.pollstation.dto.request;

import com.mlb.pollstation.enums.VoteChoice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class VoteRequestDTO {
    @NotNull
    private Long sessionId;
    @CPF
    @NotBlank(message = "cpf may not be empty or null")
    private String cpf;
    @NotNull
    private VoteChoice voteChoice;
}
