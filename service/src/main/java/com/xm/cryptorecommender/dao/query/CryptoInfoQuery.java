package com.xm.cryptorecommender.dao.query;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class CryptoInfoQuery extends MappingSqlQuery<CryptoInfo> {
    private static final String SYMBOL = "symbol";
    private static final String MAX_PRICE = "maxPrice";
    private static final String MIN_PRICE = "minPrice";
    private static final String NEWEST_PRICE = "newestPrice";
    private static final String OLDEST_PRICE = "oldestPrice";
    private static final String NEWEST_DATETIME = "newestDateTime";
    private static final String OLDEST_DATETIME = "oldestDateTime";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    public CryptoInfoQuery(DataSource ds, String sql) {
        super(ds, sql);
    }

    private static OffsetDateTime getDateTime(final ResultSet rs, final String columnLabel) throws SQLException {
        ZoneId zoneId = ZoneId.of("UTC");
        return OffsetDateTime.ofInstant(
                rs.getTimestamp(columnLabel, Calendar.getInstance(TimeZone.getTimeZone(zoneId))).toInstant(),
                zoneId);
    }

    @Override
    protected CryptoInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        var cryptoInfo = new CryptoInfo()
                .symbol(rs.getString(SYMBOL))
                .name(rs.getString(NAME))
                .description(rs.getString(DESCRIPTION))
                .maxPrice(rs.getBigDecimal(MAX_PRICE))
                .minPrice(rs.getBigDecimal(MIN_PRICE))
                .newestDateTime(CryptoInfoQuery.getDateTime(rs, NEWEST_DATETIME))
                .oldestDateTime(CryptoInfoQuery.getDateTime(rs, OLDEST_DATETIME))
                .newestPrice(rs.getBigDecimal(NEWEST_PRICE))
                .oldestPrice(rs.getBigDecimal(OLDEST_PRICE));
        return cryptoInfo.normalizedRange(cryptoInfo.getMaxPrice().subtract(cryptoInfo.getMinPrice())
                .divide(cryptoInfo.getMinPrice(), RoundingMode.HALF_EVEN));
    }
}
