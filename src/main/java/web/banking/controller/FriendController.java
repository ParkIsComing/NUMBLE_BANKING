package web.banking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.banking.dto.UserInfoDto;
import web.banking.jwt.TokenProvider;
import web.banking.service.FriendService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendController {

    private final TokenProvider tokenProvider;
    private final FriendService friendService;

    //친구 추가
    @PostMapping("/friend")
    public void addFriend(@RequestParam Long friendId, HttpServletRequest request){
        UserInfoDto userInfoDto = tokenProvider.getUserInfoByRequest(request);
        friendService.addFriend(userInfoDto.getUserId(), friendId);
    }


}
