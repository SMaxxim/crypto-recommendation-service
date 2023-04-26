package com.xm.cryptorecommender.dao.query;

import com.xm.cryptorecommender.api.model.CryptosListItem;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CryptosListQuery extends MappingSqlQuery<CryptosListItem> {
    private final static String SELECT_CRYPTOS_QUERY = """
        select
            c.symbol,
            c."name",
            (max(p.price) - min(p.price))/min(p.price) norm_range
        from
            prices p inner join cryptos c on (p.symbol = c.symbol)
        group by c.symbol, c."name", c.description
        order by norm_range desc""";
    private static final String SYMBOL = "symbol";
    private static final String NAME = "name";
    private static final String NORM_RANGE = "norm_range";

    public CryptosListQuery(final DataSource ds) {
        super(ds, SELECT_CRYPTOS_QUERY);
    }

    @Override
    protected CryptosListItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CryptosListItem()
                .symbol(rs.getString(SYMBOL))
                .name(rs.getString(NAME))
                .normalizedRange(rs.getBigDecimal(NORM_RANGE)
                        .setScale(5, RoundingMode.HALF_EVEN));
    }
}
