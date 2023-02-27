package web.banking.domain;

import lombok.*;
import web.banking.exception.NegativeBalanceException;

import javax.persistence.*;

@Entity
@Table(name="account")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="balance")
    private int balance;

    @Column(name = "account_number")
    private String accountNumber;

    public void withdraw(int money){
//        if(this.balance - money < 0){
//            throw new NegativeBalanceException("잔액이 부족합니다.");
//        }
        this.balance = balance - money;
    }
    public void deposit(int money){
        this.balance = balance + money;
    }

    public boolean isValid(Account account){
        return account.balance >= 0;
    }

    @Builder
    public Account(Long userId, String accountNumber,int balance) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
