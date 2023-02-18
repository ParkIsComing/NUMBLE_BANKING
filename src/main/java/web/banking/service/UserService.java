package web.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.banking.domain.Authority;
import web.banking.domain.User;
import web.banking.dto.UserDto;
import web.banking.exception.DuplicateMemberException;
import web.banking.exception.NotFoundMemberException;
import web.banking.repository.UserRepository;
import web.banking.util.SecurityUtil;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto){
        if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null)!=null){
            throw new DuplicateMemberException("user already exists");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username){
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));

    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities(){
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("member not found"))
        );
    }
}

