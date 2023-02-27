package web.banking.dto;

import lombok.*;
import web.banking.domain.Account;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoDto {
    private String username;
    private String accountNumber;
    private int balance;


}
