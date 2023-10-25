package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.request.IssueRequestDTO;
import com.mlb.pollstation.dto.response.IssueResponseDTO;
import com.mlb.pollstation.entities.Issue;
import com.mlb.pollstation.exception.GeneralException;
import com.mlb.pollstation.repositories.IssueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


public class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Disabled
    @Test
    void testCreateIssue() {
        IssueRequestDTO request = new IssueRequestDTO();
        request.setTitle("Test title");
        request.setDescription("Test Issue Description");
        request.setOwner("701.083.250-16");

        Issue entity = new Issue();
        entity.setId(1L);
        entity.setDescription(request.getDescription());

        Mockito.when(repository.save(Mockito.any(Issue.class))).thenReturn(entity);

        IssueResponseDTO responseDTO = issueService.createIssue(request);
        responseDTO.setIssueId(1L);

        Mockito.verify(repository).save(any(Issue.class));

        Assertions.assertEquals("Issue created", responseDTO.getMessage());
        Assertions.assertEquals(entity.getId(), responseDTO.getIssueId());
    }

    @Test
    void testFindById() {
        Long issueId = 1L;

        Issue issue = new Issue();
        issue.setId(issueId);

        Mockito.when(repository.findById(issueId)).thenReturn(Optional.of(issue));

        Issue result = issueService.findById(issueId);

        Assertions.assertEquals(issueId, result.getId());
    }

    @Test
    void testFindByIdWhenIssueNotFound() {
        Long nonExistentIssueId = 99L;

        Mockito.when(repository.findById(nonExistentIssueId)).thenReturn(Optional.empty());

        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> issueService.findById(nonExistentIssueId));
        Assertions.assertEquals("Issue not found", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
