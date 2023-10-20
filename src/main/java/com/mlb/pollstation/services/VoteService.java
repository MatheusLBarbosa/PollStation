package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.request.VoteRequestDTO;
import com.mlb.pollstation.dto.response.VoteResponseDTO;
import com.mlb.pollstation.entities.Session;
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
        log.info("Received vote: {}", request);

        Session session = sessionService.isSessionOpen(request.getSessionId());
        userHasVoted(request.getCpf(), session.getIssueId().getId());

        Vote entity = new Vote();
        BeanUtils.copyProperties(request, entity);
        entity.setSessionId(session);

        log.info("Saving vote...");
        voteRepository.save(entity);

        return new VoteResponseDTO(entity.getCpf(), entity.getSessionId().getIssueId().getTitle() ,entity.getVoteChoice());
    }

    private void userHasVoted(String cpf, Long issueId) {
        log.info("Verify if user {} has voted in session {}", cpf, issueId);
        if (voteRepository.verifyIfUserVoted(cpf, issueId))
            throw new GeneralException("User has voted", HttpStatus.BAD_REQUEST);
    }

}
