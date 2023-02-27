package web.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.banking.domain.Account;
import web.banking.domain.AccountHistory;
import web.banking.dto.AccountInfoDto;
import web.banking.dto.TransferReqDto;
import web.banking.dto.TransferResDto;
import web.banking.dto.UserInfoDto;
import web.banking.dto.request.DepositReqDto;
import web.banking.dto.request.WithdrawReqDto;
import web.banking.exception.NegativeBalanceException;
import web.banking.exception.NotAccountMatchedException;
import web.banking.exception.NotFoundAccountException;
import web.banking.exception.NotFoundMemberException;
import web.banking.repository.AccountHistoryRepository;
import web.banking.repository.AccountRepository;
import web.banking.repository.UserRelationshipRepository;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankingService {
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository historyRepository;
    private final UserRelationshipRepository userRelationshipRepository;

    public AccountInfoDto makeAccount(UserInfoDto userInfoDto){
        Random random = new Random();
        String accountNumber = String.format("%04d",random.nextInt(10000))
                + "-"+ String.format("%04d",random.nextInt(10000))
                + "-"+ String.format("%04d",random.nextInt(10000));
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(0)
                .userId(userInfoDto.getUserId())
                .build();

        accountRepository.saveAndFlush(account);
        Account newAccount = findAccount(accountNumber);

        return new AccountInfoDto(userInfoDto.getUsername(),
                newAccount.getAccountNumber(),
                newAccount.getBalance());
    }

    public AccountInfoDto checkAccount(UserInfoDto userInfoDto, String accountNumber){
        Account account = findAccount(accountNumber);
        log.info(String.valueOf(account));
        return new AccountInfoDto(userInfoDto.getUsername(),
                account.getAccountNumber(),
                account.getBalance());
    }

    public boolean isAccountOwner(Long userId, String accountNumber){
        log.info(accountNumber);
        boolean b =  accountRepository.existsByUserIdAndAccountNumber(userId, accountNumber);
        log.info(String.valueOf(b));
        return b;
    }

    @Transactional
    public AccountInfoDto depositMoney(DepositReqDto depositReqDto, UserInfoDto userInfoDto){
        Account account = accountRepository.findAccountByAccountNumber(depositReqDto.getMyAccountNumber()).orElseThrow(() -> new NotFoundMemberException("account not exists"));
        account.deposit(depositReqDto.getAmount());
        accountRepository.saveAndFlush(account);
        AccountInfoDto updatedAccount = AccountInfoDto.builder()
                .username(userInfoDto.getUsername())
                .accountNumber(depositReqDto.getMyAccountNumber())
                .balance(account.getBalance())
                .build();
        return updatedAccount;
    }

    @Transactional
    public AccountInfoDto withdrawMoney(WithdrawReqDto withdrawReqDto, UserInfoDto userInfoDto){
        Account account = accountRepository.findAccountByAccountNumber(withdrawReqDto.getMyAccountNumber()).orElseThrow(() -> new NotFoundMemberException("account not exists"));
        account.withdraw(withdrawReqDto.getAmount());
        if(!account.isValid(account)){
            throw new NegativeBalanceException("잔액이 부족합니다.");
        }
        accountRepository.saveAndFlush(account);
        AccountInfoDto updatedAccount = AccountInfoDto.builder()
                .username(userInfoDto.getUsername())
                .accountNumber(withdrawReqDto.getMyAccountNumber())
                .balance(account.getBalance())
                .build();
        return updatedAccount;
    }

    @Transactional
    public TransferResDto transferMoney(TransferReqDto transferReqDto, Long userId){
        Account sender = accountRepository.findAccountByAccountNumber(transferReqDto.getFromAccountNumber()).orElseThrow(() -> new NotFoundMemberException("sender not exists"));
        Account receiver = accountRepository.findAccountByAccountNumber(transferReqDto.getToAccountNumber()).orElseThrow(() -> new NotFoundMemberException("receiver not exists"));

        sender.withdraw(transferReqDto.getAmount());
        if(!sender.isValid(sender)){
            throw new NegativeBalanceException("잔액이 부족합니다.");
        }
        accountRepository.saveAndFlush(sender);
        receiver.deposit(transferReqDto.getAmount());
        accountRepository.saveAndFlush(receiver);

        AccountHistory history = AccountHistory.builder()
                    .amount(transferReqDto.getAmount())
                    .fromAccountNumber(transferReqDto.getFromAccountNumber())
                    .toAccountNumber(transferReqDto.getToAccountNumber())
                    .build();

        historyRepository.saveAndFlush(history);
        TransferResDto transferResDto = TransferResDto.builder()
                .senderBalance(sender.getBalance())
                .build();
        return  transferResDto;
    }

    public Account findAccount(String accountNumber){
        return accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundAccountException("account not exists"));

    }

    public boolean isValidTransfer(TransferReqDto transferReqDto, UserInfoDto userInfoDto){
        Long senderId = accountRepository.findAccountByAccountNumber(transferReqDto.getFromAccountNumber())
                    .get().getUserId();
        Long receiverId = accountRepository.findAccountByAccountNumber(transferReqDto.getToAccountNumber())
                    .get().getUserId();

        if(senderId < receiverId){
            return userRelationshipRepository.existsByUserFirstIdAndUserSecondId(senderId, receiverId);
        }else{
            return userRelationshipRepository.existsByUserFirstIdAndUserSecondId(receiverId, senderId);
        }
    }
}
