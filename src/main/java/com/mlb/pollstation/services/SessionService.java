package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.response.SessionResponseDTO;
import com.mlb.pollstation.entities.Issue;
import com.mlb.pollstation.entities.Session;
import com.mlb.pollstation.enums.SessionStatus;
import com.mlb.pollstation.exception.GeneralException;
import com.mlb.pollstation.repositories.IssueRepository;
import com.mlb.pollstation.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final IssueRepository issueRepository;
    private final IssueService issueService;

    public SessionResponseDTO startSession(Long issueId){
        log.info("Starting session for issue id: {}", issueId);

        Issue issue = issueService.findById(issueId);

        Session session = new Session();
        session.setIssueId(issue);
        session.setSessionStatus(SessionStatus.OPEN_SESSION);
        sessionRepository.save(session);

        issue.setSession(session);
        issueRepository.save(issue);

        log.info("Session {} created with success", session.getId());
        return new SessionResponseDTO("session created");
    }

    public Session isSessionOpen(Long sessionId) {
        log.info("Searching for session with id: {}", sessionId);
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new GeneralException("This session may not exist", HttpStatus.BAD_REQUEST));
        validateSession(session);

        return session;
    }

    private void validateSession(Session session) {
        log.info("Validate session: {}", session);
        if (session.getSessionStatus() == SessionStatus.CLOSED_SESSION)
            throw new GeneralException("This session is closed", HttpStatus.NOT_ACCEPTABLE);
        if (session.getFinishedAt().isBefore(LocalDateTime.now()))
            throw new GeneralException("Expired session", HttpStatus.NOT_ACCEPTABLE);
    }

}
