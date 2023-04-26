package com.xm.cryptorecommender.dao.repository;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.api.model.CryptosListItem;
import com.xm.cryptorecommender.dao.query.BestCryptoByDateQuery;
import com.xm.cryptorecommender.dao.query.CryptoInfoBySymbolQuery;
import com.xm.cryptorecommender.dao.query.CryptosListQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CryptoRepository {
    private final CryptoInfoBySymbolQuery cryptoBySymbolQuery;
    private final BestCryptoByDateQuery bestCryptoByDateQuery;
    private final CryptosListQuery cryptosListQuery;

    public CryptoRepository(final CryptoInfoBySymbolQuery cryptoBySymbolQuery,
                            final BestCryptoByDateQuery bestCryptoByDateQuery,
                            final CryptosListQuery cryptosListQuery) {
        this.cryptoBySymbolQuery = cryptoBySymbolQuery;
        this.bestCryptoByDateQuery = bestCryptoByDateQuery;
        this.cryptosListQuery = cryptosListQuery;
    }

    public Optional<CryptoInfo> getCryptoInfoBySymbol(final String symbol) {
        return Optional.ofNullable(cryptoBySymbolQuery.findObject(symbol));
    }

    public Optional<CryptoInfo> getBestCryptoByDate(final LocalDate date) {
        LocalDateTime startTimeStamp = date.atStartOfDay();
        LocalDateTime endTimeStamp = date.plusDays(1).atStartOfDay();
        return Optional.ofNullable(bestCryptoByDateQuery.findObject(startTimeStamp, endTimeStamp,
                startTimeStamp, endTimeStamp));
    }

    public List<CryptosListItem> getCryptosList() {
        return cryptosListQuery.execute();
    }
}
