package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.request.VoteRequestDTO;
import com.mlb.pollstation.dto.response.VoteResponseDTO;
import com.mlb.pollstation.entities.Vote;
import com.mlb.pollstation.exception.GeneralException;
import com.mlb.pollstation.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final SessionService sessionService;

    public VoteResponseDTO voteInIssue(VoteRequestDTO request){
        log.info("Received vote {}", request);

        Vote vote = new Vote();
        BeanUtils.copyProperties(request, vote);

        sessionService.isSessionOpen(vote.getId());
        userHasVoted(vote.getCpf(), vote.getId());

        log.info("Saving vote...");
        voteRepository.save(vote);

        return new VoteResponseDTO(vote.getCpf(), vote.getVoteChoice());
    }

    private void userHasVoted(String cpf, Long sessionId) {
        if (voteRepository.verifyIfUserVoted(cpf, sessionId))
            throw new GeneralException("User has voted", HttpStatus.BAD_REQUEST);
    }

}
