package com.mlb.pollstation.dto.response;

public interface TotalVotesResponseDTO {
     String getTitle();
     Long getSessionId();
     Integer getTotalVotes();
     Integer getYesVotes();
     Integer getNoVotes();
}
