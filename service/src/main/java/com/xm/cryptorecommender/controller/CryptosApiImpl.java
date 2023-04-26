package com.xm.cryptorecommender.controller;

import com.xm.cryptorecommender.api.CryptosApiDelegate;
import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.api.model.CryptosListItem;
import com.xm.cryptorecommender.service.CryptosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CryptosApiImpl implements CryptosApiDelegate {
    private final CryptosService cryptosService;

    public CryptosApiImpl(final CryptosService cryptosService) {
        this.cryptosService = cryptosService;
    }

    @Override
    public ResponseEntity<CryptoInfo> getCryptoInfoBySymbol(String cryptoSymbol) {
        return cryptosService.getCryptoInfoBySymbol(cryptoSymbol)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Requested cryptocurrency not found"));
    }

    @Override
    public ResponseEntity<List<CryptosListItem>> getCryptosList() {
        return ResponseEntity.ok(cryptosService.getCryptosList());
    }
}
