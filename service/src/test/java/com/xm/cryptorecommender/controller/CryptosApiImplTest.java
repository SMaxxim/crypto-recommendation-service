package com.xm.cryptorecommender.controller;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.service.CryptosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptosApiImplTest {

    @Mock
    CryptosService cryptosService;

    @InjectMocks
    CryptosApiImpl underTest;

    @Test
    public void testGetCryptoInfoBySymbolReturnValueIfFound() {
        // given
        when(cryptosService.getCryptoInfoBySymbol(any())).thenReturn(Optional.of(new CryptoInfo()));
        // when
        ResponseEntity<CryptoInfo> response = underTest.getCryptoInfoBySymbol("BTC");
        // then
        assertThat(response).isEqualTo(ResponseEntity.ok(new CryptoInfo()));
    }

    @Test
    public void testGetCryptoInfoBySymbolThrowExceptionIfNotFound() {
        // given
        when(cryptosService.getCryptoInfoBySymbol(any())).thenReturn(Optional.empty());
        // when
        assertThatThrownBy(() -> underTest.getCryptoInfoBySymbol("BTC"))
                .isInstanceOf(ResponseStatusException.class);
    }

}