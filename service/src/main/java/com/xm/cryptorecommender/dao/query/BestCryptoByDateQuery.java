package com.xm.cryptorecommender.dao.query;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class BestCryptoByDateQuery extends CryptoInfoQuery {
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
            p.price_timestamp >= ? and
            p.price_timestamp < ? and
            p.symbol = (select symbol from (
                select
                    p.symbol,
                    (max(p.price) - min(p.price))/min(p.price) norm_range
                from
                    prices p
                where
                    p.price_timestamp >= ? and p.price_timestamp < ?
                group by p.symbol
                order by norm_range desc
                fetch first 1 rows only) best_crypto)
        fetch first 1 rows only""";

    public BestCryptoByDateQuery(final DataSource ds) {
        super(ds, SELECT_CRYPTO_INFO);
        super.declareParameter(new SqlParameter(Types.TIMESTAMP));
        super.declareParameter(new SqlParameter(Types.TIMESTAMP));
        super.declareParameter(new SqlParameter(Types.TIMESTAMP));
        super.declareParameter(new SqlParameter(Types.TIMESTAMP));
    }

}
