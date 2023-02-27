package web.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.banking.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountNumber(String accountNumber);

    boolean existsByUserIdAndAccountNumber(Long userId, String AccountNumber);




}
