package com.mlb.pollstation.services;

import com.mlb.pollstation.dto.request.IssueRequestDTO;
import com.mlb.pollstation.dto.response.IssueResponseDTO;
import com.mlb.pollstation.entities.Issue;
import com.mlb.pollstation.exception.GeneralException;
import com.mlb.pollstation.repositories.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository repository;

    public IssueResponseDTO createIssue(IssueRequestDTO request){
        log.info("Creating issue: {}", request);
        Issue entity = new Issue();
        BeanUtils.copyProperties(request, entity);
        repository.save(entity);

        return new IssueResponseDTO("Issue created", entity.getId());
    }

    public Issue findById(Long issueId) {
        return repository.findById(issueId).orElseThrow(
                () -> new GeneralException("Issue not found", HttpStatus.NOT_FOUND)
        );
    }
}
