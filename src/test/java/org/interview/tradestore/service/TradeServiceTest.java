package org.interview.tradestore.service;

import org.interview.tradestore.dto.TradeStore;
import org.interview.tradestore.dto.TradeStorePk;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(value = {"/data.sql"})
public class TradeServiceTest {
    @Autowired
    TradeService service;

    @Test
    public void saveNewTradeTest() {
        int sizeBefore = service.getAllActiveTrades().size();
        TradeStore ts = TradeStore.builder()
                .tradeId("T1")
                .version(1)
                .bookId("BK-1")
                .maturityDate(LocalDate.now())
                .createDate(LocalDate.now())
                .cpId("CP-22")
                .expired("N")
                .build();
        service.saveTrade(ts);
        int sizeAfter = service.getAllActiveTrades().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Assert.assertTrue(service.getAllActiveTrades().contains(ts));
    }

    @Test(expected = RuntimeException.class)
    public void saveOutdatedTradeTest() {
        TradeStore ts = TradeStore.builder()
                .tradeId("T2")
                .version(1)
                .bookId("BK-1")
                .maturityDate(LocalDate.now())
                .createDate(LocalDate.now())
                .cpId("CP-22")
                .expired("N")
                .build();
        service.saveTrade(ts);
    }

    @Test
    public void amendTradeTest() {
        int sizeBefore = service.getAllActiveTrades().size();
        TradeStore ts = TradeStore.builder()
                .tradeId("T2")
                .version(2)
                .bookId("B-1")
                .maturityDate(LocalDate.now())
                .createDate(LocalDate.now())
                .cpId("CP-1")
                .expired("N")
                .build();
        service.saveTrade(ts);
        int sizeAfter = service.getAllActiveTrades().size();
        Assert.assertEquals(sizeBefore, sizeAfter);
        Assert.assertTrue(service.getAllActiveTrades().contains(ts));
    }

    @Test
    public void saveUpdatedTradeTest() {
        int sizeBefore = service.getAllActiveTrades().size();
        TradeStore ts = TradeStore.builder()
                .tradeId("T2")
                .version(4)
                .bookId("B-1")
                .maturityDate(LocalDate.now())
                .createDate(LocalDate.now())
                .cpId("CP-1")
                .expired("N")
                .build();
        service.saveTrade(ts);
        int sizeAfter = service.getAllActiveTrades().size();
        Assert.assertEquals(sizeBefore + 1, sizeAfter);
        Assert.assertTrue(service.getAllActiveTrades().contains(ts));
    }

    @Test
    public void flipExpiryFlagTest() {
        List<TradeStore> ts = service.getAllActiveTrades();
        Map<String, TradeStore> lookUp = ts.stream().collect(Collectors.toMap(TradeStore::getTradeId, TradeStore -> TradeStore));
        Assert.assertTrue("N".equals(lookUp.get("T992").getExpired()));
        service.flipExpiredTrades();
        ts = service.getAllActiveTrades();
        lookUp = ts.stream().collect(Collectors.toMap(TradeStore::getTradeId, TradeStore -> TradeStore));
        Assert.assertFalse(lookUp.containsKey("T992"));
    }
}
