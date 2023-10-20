package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.response.SessionResponseDTO;
import com.mlb.pollstation.entities.Session;
import com.mlb.pollstation.enums.SessionStatus;
import com.mlb.pollstation.exception.GeneralException;
import com.mlb.pollstation.repositories.IssueRepository;
import com.mlb.pollstation.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final IssueRepository issueRepository;

    public SessionResponseDTO startSession(Long issueId){
        issueRepository.findById(issueId).ifPresentOrElse(issue -> {
            Session session = new Session();
            session.setIssueId(issue);
            session.setSessionStatus(SessionStatus.OPEN_SESSION);

            issue.setSession(session);
            sessionRepository.save(session);
            issueRepository.save(issue);
        }, () -> {
            throw new GeneralException("issue_id not found", HttpStatus.NOT_FOUND);
        });

        return new SessionResponseDTO("session created");
    }

    public void isSessionOpen(Long sessionId) {
        sessionRepository.findById(sessionId).ifPresentOrElse(this::validateSession, () -> {
            throw new GeneralException("This session may not exist", HttpStatus.BAD_REQUEST);
        });
    }

    private void validateSession(Session session) {
        if (session.getSessionStatus() == SessionStatus.CLOSED_SESSION)
            throw new GeneralException("This session is closed", HttpStatus.NOT_ACCEPTABLE);
        if (session.getFinishedAt().isBefore(LocalDateTime.now()))
            throw new GeneralException("Expired session", HttpStatus.NOT_ACCEPTABLE);
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void scheduleTaskUsingCronExpression() {
        System.out.println("My schedule");
    }
}
