package com.xm.cryptorecommender.service;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.dao.repository.CryptoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RecommendationsService {
    private final CryptoRepository cryptoRepository;

    public RecommendationsService(final CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    public Optional<CryptoInfo> getHighestRangeCryptoForDate(LocalDate date) {
        return cryptoRepository.getBestCryptoByDate(date);
    }
}
