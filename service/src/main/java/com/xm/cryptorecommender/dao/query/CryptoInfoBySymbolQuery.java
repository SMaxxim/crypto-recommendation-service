package com.xm.cryptorecommender.dao.query;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class CryptoInfoBySymbolQuery extends CryptoInfoQuery {
    private static final String SELECT_CRYPTO_INFO = """
            select
                c.symbol,
                c."name",
                c.description,
                min(p.price) over () minPrice,
                max(p.price) over () maxPrice,
                first_value(p.price_timestamp) over (
                    order by p.price_timestamp desc range between unbounded preceding and unbounded following
                    ) newestDateTime,
                last_value(p.price_timestamp) over (
                    order by p.price_timestamp desc range between unbounded preceding and unbounded following
                    ) oldestDateTime,
                first_value(p.price) over (
                    order by p.price_timestamp desc range between unbounded preceding and unbounded following
                    ) newestPrice,
                last_value(p.price) over (
                    order by p.price_timestamp desc range between unbounded preceding and unbounded following
                    ) oldestPrice
            from
                prices p inner join cryptos c on
                (p.symbol = c.symbol)
            where
                p.symbol = ?
            fetch first 1 rows only""";

    public CryptoInfoBySymbolQuery(final DataSource ds) {
        super(ds, SELECT_CRYPTO_INFO);
        super.declareParameter(new SqlParameter(Types.VARCHAR));
    }

}
