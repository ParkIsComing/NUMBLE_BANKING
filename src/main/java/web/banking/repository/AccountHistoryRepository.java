package web.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.banking.domain.AccountHistory;

import java.util.List;
import java.util.Optional;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory,Long> {
    List<Optional<AccountHistory>> findAccountHistoriesByToAccountNumberAndFromAccountNumber(String toAccountNumber, String fromAccountNumber);
}
