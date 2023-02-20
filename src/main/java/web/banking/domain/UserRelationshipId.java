package web.banking.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserRelationshipId implements Serializable { //식별자 클래스
    @Column(name = "user_first_id")
    private Long userFirstId;
    @Column(name = "user_second_id")
    private Long userSecondId;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass()!= obj.getClass()) return false;
        UserRelationshipId that = (UserRelationshipId) obj;
        return userFirstId.equals(that.userFirstId) && userSecondId.equals(that.userSecondId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userFirstId, userSecondId);
    }
}
