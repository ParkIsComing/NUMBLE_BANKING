package web.banking.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_history")
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long accountHistoryId;
    private Double amount;
    private LocalDateTime createdAt;
    private Long senderId;
    private Long receiverId;
    private Double balance;


}

