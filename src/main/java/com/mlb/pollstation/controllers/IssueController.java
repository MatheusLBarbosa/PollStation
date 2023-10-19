package com.mlb.pollstation.controllers;

import com.mlb.pollstation.dto.request.IssueRequestDTO;
import com.mlb.pollstation.dto.response.IssueResponseDTO;
import com.mlb.pollstation.services.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issue")
public class IssueController {

    private final IssueService service;
    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@RequestBody @Validated final IssueRequestDTO request) {
        return new ResponseEntity<>(service.createIssue(request), HttpStatus.CREATED);
    }
}
