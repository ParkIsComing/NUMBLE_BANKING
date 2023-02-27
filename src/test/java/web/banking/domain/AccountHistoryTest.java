package web.banking.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountHistoryTest {
    @Test
    @DisplayName("송금시에 AccountHistory에 기록이 남는다.")
    void createAccountHistory(){
        assertDoesNotThrow(()->new AccountHistory(5000, "1111-2222,3333", "2222-3333-4444"));
    }
    @Test
    @DisplayName("송금시에 AccountHistory에 기록이 남는다2.")
    void createAccountHistory2(){
        assertDoesNotThrow(()-> AccountHistory.builder()
                .amount(5000)
                .fromAccountNumber("1111-2222,3333")
                .toAccountNumber("2222-3333-4444")
                .build());
    }

}