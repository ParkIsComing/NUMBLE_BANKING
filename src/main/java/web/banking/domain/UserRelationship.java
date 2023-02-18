package web.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "user_relationship")
@IdClass(UserRelationshipId.class)
public class UserRelationship { //userFistId must be bigger than userSecondId
    @Id
    @Column(name="user_first_id")
    private Long userFirstId;

    @Id
    @Column(name = "user_second_id")
    private Long userSecondId;

}
