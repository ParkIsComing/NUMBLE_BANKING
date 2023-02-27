package web.banking.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.banking.exception.NegativeBalanceException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    @DisplayName("계좌 생성")
    void createAccount(){
        assertDoesNotThrow(
                ()->Account.builder()
                        .accountNumber("1111-2222-3333")
                        .userId(7L)
                        .balance(0)
                        .build()
        );
    }

    @Test
    @DisplayName("계좌에 돈을 입금할 수 있다.")
    void deposit(){
        Account account = Account.builder()
                .balance(0)
                .accountNumber("1111-2222-3333")
                .userId(1l)
                .build();
        account.deposit(3000);
        assertThat(account.getBalance()).isEqualTo(3000);
    }

    @Test
    @DisplayName("계좌에서 돈을 출금할 수 있다.")
    void withdraw(){
        Account account = Account.builder()
                .balance(3000)
                .accountNumber("1111-2222-3333")
                .userId(1l)
                .build();
        account.withdraw(2000);
        account.isValid(account);
        assertThat(account.getBalance()).isEqualTo(1000);
    }

    @Test
    @DisplayName("잔금이 모자라면 출금이 불가능하다.")
    void withdrawNegativeBalance(){
        Account account = new Account(1l, "1111-2222-3333",3000);
        account.withdraw(4000);

        assertThrows(NegativeBalanceException.class, ()->{
            account.isValid(account);
        });
    }

}