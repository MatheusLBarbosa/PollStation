package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.request.VoteRequestDTO;
import com.mlb.pollstation.dto.response.TotalVotesResponseDTO;
import com.mlb.pollstation.dto.response.VoteResponseDTO;
import com.mlb.pollstation.entities.Issue;
import com.mlb.pollstation.entities.Session;
import com.mlb.pollstation.entities.Vote;
import com.mlb.pollstation.enums.VoteChoice;
import com.mlb.pollstation.repositories.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVoteInIssue() {
        VoteRequestDTO request = new VoteRequestDTO();
        request.setCpf("1234567890");
        request.setSessionId(1L);
        request.setVoteChoice(VoteChoice.YES);

        Issue issue = new Issue();
        issue.setId(2L);
        issue.setTitle("Test Issue");
        Session session = new Session();
        session.setId(1L);
        session.setIssueId(issue);

        Mockito.when(sessionService.verifyIfSessionExist(1L)).thenReturn(session);

        Mockito.doNothing().when(sessionService).validateSession(session);

        Mockito.when(voteRepository.save(any(Vote.class))).thenAnswer(invocation -> {
            Vote savedVote = invocation.getArgument(0);
            savedVote.setId(1L);
            return savedVote;
        });

        VoteResponseDTO response = voteService.voteInIssue(request);

        Mockito.verify(sessionService).verifyIfSessionExist(1L);
        Mockito.verify(sessionService).validateSession(session);
        Mockito.verify(voteRepository).save(any(Vote.class));

        assertEquals("1234567890", response.getCpf());
        assertEquals("Test Issue", response.getIssue());
        assertEquals(VoteChoice.YES, response.getVoteChoice());
    }

    @Test
    public void testCountingOfVotes() {
        Session session = new Session();
        session.setId(1L);

        Mockito.when(sessionService.verifyIfSessionExist(1L)).thenReturn(session);

        TotalVotesResponseDTO voteDetails = voteRepository.getSessionVoteDetails(1L);
        Mockito.when(voteDetails).thenReturn(any(TotalVotesResponseDTO.class));

        TotalVotesResponseDTO result = voteService.countingOfVotes(1L);

        Mockito.verify(sessionService).verifyIfSessionExist(1L);
        Mockito.verify(voteRepository).getSessionVoteDetails(1L);

        assertEquals(result, voteDetails);
    }
}
