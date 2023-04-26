package com.xm.cryptorecommender;

import com.xm.cryptorecommender.api.model.CryptoInfo;
import com.xm.cryptorecommender.api.model.CryptosListItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {
                "spring.liquibase.enabled=false",
                "spring.sql.init.data-locations=classpath:mock_data.sql" })
@AutoConfigureTestDatabase
public class CryptosApiTest {

    @Value(value="${local.server.port}")
    private int port;

    private WebTestClient testClient() {
        return WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:"+ port)
                .build();
    }

    @Test
    void testCryptosList() {
        testClient()
                .get().uri("/cryptos")
                .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(CryptosListItem.class).contains(
                            new CryptosListItem()
                                    .symbol("ETH")
                                    .name("Ethereum")
                                    .normalizedRange(new BigDecimal("0.2")),
                            new CryptosListItem()
                                    .symbol("BTC")
                                    .name("Bitcoin")
                                    .normalizedRange(new BigDecimal("0.1")));
    }

    @Test
    void testCryptoInfo() {
        testClient()
                .get().uri("/cryptos/BTC")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CryptoInfo.class).isEqualTo(
                        new CryptoInfo()
                                .symbol("BTC")
                                .name("Bitcoin")
                                .minPrice(new BigDecimal("10.00000"))
                                .maxPrice(new BigDecimal("11.00000"))
                                .newestDateTime(OffsetDateTime.of(2022, 1 ,2,
                                                10, 0,0,0,
                                                ZoneOffset.UTC))
                                .oldestDateTime(OffsetDateTime.of(2022, 1 ,1,
                                        4, 0,0,0,
                                        ZoneOffset.UTC))
                                .newestPrice(new BigDecimal("11.00000"))
                                .oldestPrice(new BigDecimal("10.00000"))
                                .normalizedRange(new BigDecimal("0.10000")));
    }

    @Test
    void testRecommendations() {
        testClient()
                .get().uri("/recommendations?date=2022-01-01")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CryptoInfo.class).isEqualTo(
                        new CryptoInfo()
                                .symbol("BTC")
                                .name("Bitcoin")
                                .minPrice(new BigDecimal("10.00000"))
                                .maxPrice(new BigDecimal("10.50000"))
                                .newestDateTime(OffsetDateTime.of(2022, 1 ,1,
                                        5, 0,0,0,
                                        ZoneOffset.UTC))
                                .oldestDateTime(OffsetDateTime.of(2022, 1 ,1,
                                        4, 0,0,0,
                                        ZoneOffset.UTC))
                                .newestPrice(new BigDecimal("10.50000"))
                                .oldestPrice(new BigDecimal("10.00000"))
                                .normalizedRange(new BigDecimal("0.05000")));
    }

}