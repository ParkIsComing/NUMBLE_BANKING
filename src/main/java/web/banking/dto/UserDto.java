package web.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.banking.domain.User;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    @NotNull
    private String username;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user){
        if(user==null){
            return null;
        }

        return UserDto.builder()
                .username(user.getUsername())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
