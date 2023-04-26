package com.xm.cryptorecommender.service;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.api.model.CryptosListItem;
import com.xm.cryptorecommender.dao.repository.CryptoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CryptosService {
    private final CryptoRepository cryptoRepository;

    public CryptosService(final CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    public Optional<CryptoInfo> getCryptoInfoBySymbol(String symbol) {
        return cryptoRepository.getCryptoInfoBySymbol(symbol);
    }

    public List<CryptosListItem> getCryptosList() {
        return cryptoRepository.getCryptosList();
    }
}
