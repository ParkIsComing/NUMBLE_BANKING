package web.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRelationshipId implements Serializable { //식별자 클래스
    private Long userFirstId;
    private Long userSecondId;

}
