package web.banking.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_history")
@NoArgsConstructor
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long accountHistoryId;
    private int amount;
    private LocalDateTime createdAt;

//    @Embedded
//    @AttributeOverrides(@AttributeOverride(name="accountNumber", column = @Column(name="fromAccountNumber")))
    private String fromAccountNumber;
//
//    @Embedded
//    @AttributeOverrides(@AttributeOverride(name="accountNumber", column = @Column(name="toAccountNumber")))
    private String toAccountNumber;

    @Builder
    public AccountHistory(int amount, String fromAccountNumber, String toAccountNumber) {
        this.amount = amount;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.createdAt = LocalDateTime.now();
    }
}

