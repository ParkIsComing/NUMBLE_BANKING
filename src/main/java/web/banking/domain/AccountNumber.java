package web.banking.domain;


import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;


@Embeddable
@Data
public class AccountNumber {

//    private static final Pattern PATTERN =
//    @Column(name = "account_number", unique = true)
//    @Pattern(regexp = pattern)
//    private final String accountNumber;
//
//    public AccountNumber() {
//    }
//
//    public AccountNumber(String accountNumber) {
//        this.accountNumber = validate(accountNumber);
//    }
//
//    private String validate(String accountNumber) {
//        if (StringUtils.hasText(accountNumber) || Pattern.matches(pattern,accountNumber)) {
//            return "111-111-111-111"; //exception으로 수정
//        }
//        return accountNumber;
//    }
}
