package org.interview.tradestore.controller;

import org.interview.tradestore.dto.TradeStore;
import org.interview.tradestore.service.TradeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade-store")
public class TradeProducer {

    private TradeService service;

    public TradeProducer(TradeService service) {
        this.service = service;
    }

    @PostMapping("/accept")
    public String accept(@RequestBody TradeStore trade) {
        service.saveTrade(trade);
        return new String("Trade Accepted");
    }

    @GetMapping("/all-active")
    public List<TradeStore> getAll() {
        return service.getAllActiveTrades();
    }

    //Every hour
    @Scheduled(fixedRate=60*60*1000)
    public void flipExpiredTrades() {
        service.flipExpiredTrades();
    }
}
