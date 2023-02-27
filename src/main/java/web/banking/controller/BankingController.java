package web.banking.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import web.banking.dto.AccountInfoDto;
import web.banking.dto.TransferReqDto;
import web.banking.dto.TransferResDto;
import web.banking.dto.UserInfoDto;
import web.banking.dto.request.DepositReqDto;
import web.banking.dto.request.WithdrawReqDto;
import web.banking.exception.NotAccountMatchedException;
import web.banking.exception.NotFoundAccountException;
import web.banking.exception.NotFoundRelationshipException;
import web.banking.service.BankingService;
import web.banking.jwt.TokenProvider;
import web.banking.lock.UserLevelLock;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/banking")
@RequiredArgsConstructor
@Slf4j
public class BankingController {
    private final UserLevelLock userLevelLock;
    private final TokenProvider tokenProvider;
    private final BankingService bankingService;
    private static final int LOCK_TIMEOUT_SECONDS = 3;

    //계좌 생성
    @GetMapping("/account")
    public AccountInfoDto makeAccount(HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        return bankingService.makeAccount(userInfoDto);
    }

    //계좌 조회
    @PostMapping("/account")
    public AccountInfoDto checkAccount(@RequestParam String account, HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        if(bankingService.isAccountOwner(userInfoDto.getUserId(),account)){
            return bankingService.checkAccount(userInfoDto,account);
        }
        return null;
    }

    //입금
    @PostMapping("/transfer/deposit")
    public AccountInfoDto depositMoneey(@RequestBody DepositReqDto depositReqDto, HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        return userLevelLock.executeWithLock(
                String.valueOf(userInfoDto.getUserId()),
                LOCK_TIMEOUT_SECONDS,
                ()->bankingService.depositMoney(depositReqDto, userInfoDto));
    }

    //출금
    @PostMapping("/transfer/withdraw")
    public AccountInfoDto withdrawMoney(@RequestBody WithdrawReqDto withdrawReqDto, HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        return userLevelLock.executeWithLock(
                String.valueOf(userInfoDto.getUserId()),
                LOCK_TIMEOUT_SECONDS,
                ()->bankingService.withdrawMoney(withdrawReqDto, userInfoDto));
    }

    //친구간 송금
    @PostMapping("/transfer" )
    public TransferResDto transferMoney(@RequestBody TransferReqDto transferReqDto, HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);

        if(!bankingService.isValidTransfer(transferReqDto, userInfoDto)){
            throw new NotFoundRelationshipException("친구 등록된 관계가 아닙니다.");
        };

        return userLevelLock.executeWithLock(
                String.valueOf(userInfoDto.getUserId()),
                LOCK_TIMEOUT_SECONDS,
                ()-> bankingService.transferMoney(transferReqDto, userInfoDto.getUserId())
        );

    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleError(IllegalStateException exception) {
        String message = exception.getMessage();
        log.error(message);
        return message;
    }
}
