package org.interview.tradestore.service;

import org.interview.tradestore.dto.TradeStore;
import org.interview.tradestore.repository.TradeStoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private TradeStoreRepository repository;

    public TradeService(TradeStoreRepository repository) {
        this.repository = repository;
    }

    public List<TradeStore> getAllActiveTrades() {
        return repository.findAll();
    }

    public void saveTrade(TradeStore tradeStore) {
        Optional<TradeStore> opLatestTrade = repository.findLatestTradeVersionByTradeId(tradeStore.getTradeId());

        if(opLatestTrade.isPresent()) {
            if(opLatestTrade.get().getVersion() > tradeStore.getVersion()) {
                throw new RuntimeException("Trade Rejected!");
            } else if(opLatestTrade.get().getVersion() == tradeStore.getVersion()) {
                // Amend Trade
                TradeStore t = opLatestTrade.get();
                t.setBookId(tradeStore.getBookId());
                t.setCpId(tradeStore.getCpId());
                t.setExpired(tradeStore.getExpired());
                t.setMaturityDate(tradeStore.getMaturityDate());
                t.setCreateDate(tradeStore.getCreateDate());
                repository.save(t);
                return;
            }
        }
            //Create New
            repository.save(tradeStore);
    }

    @Transactional
    public void flipExpiredTrades() {
        repository.flipExpiredTrades(LocalDate.now());
    }
}
