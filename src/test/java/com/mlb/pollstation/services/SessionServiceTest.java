package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.response.SessionResponseDTO;
import com.mlb.pollstation.entities.Issue;
import com.mlb.pollstation.entities.Session;
import com.mlb.pollstation.enums.SessionStatus;
import com.mlb.pollstation.exception.GeneralException;
import com.mlb.pollstation.repositories.IssueRepository;
import com.mlb.pollstation.repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueService issueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartSession() {
        Issue issue = new Issue();
        issue.setId(1L);
        Session session = new Session();
        session.setId(1L);

        Mockito.when(issueService.findById(1L)).thenReturn(issue);

        Mockito.when(sessionRepository.save(any(Session.class))).thenReturn(session);

        SessionResponseDTO response = sessionService.startSession(1L);

        Mockito.verify(issueService).findById(1L);
        Mockito.verify(sessionRepository).save(any(Session.class));
        Mockito.verify(issueRepository).save(issue);

        assertEquals("session created", response.getMessage());
    }

    @Test
    public void testVerifyIfSessionExistWhenSessionExists() {
        Session session = new Session();
        session.setId(1L);

        Mockito.when(sessionRepository.findById(1L)).thenReturn(java.util.Optional.of(session));

        Session result = sessionService.verifyIfSessionExist(1L);

        Mockito.verify(sessionRepository).findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testVerifyIfSessionExistWhenSessionDoesNotExist() {
        Mockito.when(sessionRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        assertThrows(GeneralException.class, () -> {
            sessionService.verifyIfSessionExist(2L);
        });
    }

    @Test
    public void testValidateSessionWhenSessionIsClosed() {
        Session session = new Session();
        session.setSessionStatus(SessionStatus.CLOSED_SESSION);

        assertThrows(GeneralException.class, () -> {
            sessionService.validateSession(session);
        });
    }

    @Test
    public void testValidateSessionWhenSessionIsExpired() {
        Session session = new Session();
        session.setSessionStatus(SessionStatus.CLOSED_SESSION);
        session.setFinishedAt(LocalDateTime.now().minusHours(1));

        assertThrows(GeneralException.class, () -> {
            sessionService.validateSession(session);
        });
    }

    @Disabled
    @Test
    public void testValidateSessionWhenSessionIsValid() {
        Session session = new Session();
        session.setSessionStatus(SessionStatus.OPEN_SESSION);
        session.setFinishedAt(LocalDateTime.now().plusHours(1));

        assertDoesNotThrow(() -> {
            sessionService.validateSession(session);
        });
    }
}

