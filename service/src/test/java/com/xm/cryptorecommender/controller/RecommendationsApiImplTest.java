package com.xm.cryptorecommender.controller;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.service.RecommendationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationsApiImplTest {

    @Mock
    RecommendationsService recommendationsService;

    @InjectMocks
    RecommendationsApiImpl underTest;

    @Test
    public void testGetCryptoInfoBySymbolReturnValueIfFound() {
        // given
        when(recommendationsService.getHighestRangeCryptoForDate(any()))
                .thenReturn(Optional.of(new CryptoInfo()));
        // when
        ResponseEntity<CryptoInfo> response = underTest.getHighestRangeCryptoForDate(LocalDate.now());
        // then
        assertThat(response).isEqualTo(ResponseEntity.ok(new CryptoInfo()));
    }

    @Test
    public void testGetCryptoInfoBySymbolThrowExceptionIfNotFound() {
        // given
        when(recommendationsService.getHighestRangeCryptoForDate(any())).thenReturn(Optional.empty());
        // when
        assertThatThrownBy(() -> underTest.getHighestRangeCryptoForDate(LocalDate.now()))
                .isInstanceOf(ResponseStatusException.class);
    }


}