package org.interview.tradestore.repository;

import org.interview.tradestore.dto.TradeStore;
import org.interview.tradestore.dto.TradeStorePk;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TradeStoreRepository extends CrudRepository<TradeStore, TradeStorePk> {

    @Override
    @Query("select p from TradeStore p where p.expired = 'N'")
    List<TradeStore> findAll();

    @Query("Select t from TradeStore t where t.expired = 'N' and t.tradeId = :tradeId and t.version = (select max(p.version) from TradeStore p where p.tradeId = :tradeId and p.expired = 'N')")
    Optional<TradeStore> findLatestTradeVersionByTradeId(@Param("tradeId") String tradeId);

    @Modifying
    @Query("Update TradeStore t set t.expired = 'Y' where t.maturityDate < :today")
    void flipExpiredTrades(@Param("today") LocalDate today);

}
