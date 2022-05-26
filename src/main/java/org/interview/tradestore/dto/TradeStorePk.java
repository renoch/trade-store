package org.interview.tradestore.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class TradeStorePk implements Serializable {

    @Column(name = "TRADE_ID", nullable = false)
    private String tradeId;

    @Column(name = "VERSION", nullable = false)
    private int version;
}
