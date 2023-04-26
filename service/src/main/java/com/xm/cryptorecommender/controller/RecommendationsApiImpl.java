package com.xm.cryptorecommender.controller;

import com.xm.cryptorecommender.api.RecommendationsApi;
import com.xm.cryptorecommender.api.RecommendationsApiDelegate;
import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.service.RecommendationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class RecommendationsApiImpl implements RecommendationsApiDelegate {
    private final RecommendationsService recommendationsService;

    public RecommendationsApiImpl(final RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @Override
    public ResponseEntity<CryptoInfo> getHighestRangeCryptoForDate(LocalDate date) {
        return recommendationsService.getHighestRangeCryptoForDate(date)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No information about this date"));
    }
}
