package org.interview.tradestore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@IdClass(TradeStorePk.class)
@Table(name = "TRADE_STORE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TradeStore {
    @Id
    @Column(name = "TRADE_ID", nullable = false)
    private String tradeId;

    @Id
    @Column(name = "VERSION", nullable = false)
    private int version;

    @Column(name = "CREATE_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @Column(name = "MATURITY_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate maturityDate;

    @Column(name = "CP_ID", nullable = false)
    private String cpId;

    @Column(name = "BOOK_ID", nullable = false)
    private String bookId;

    @Column(name = "EXPIRED")
    private String expired;

}
