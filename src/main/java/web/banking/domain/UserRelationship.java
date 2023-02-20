package web.banking.domain;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "user_relationship")
public class UserRelationship { //userFistId must be bigger than userSecondId

    @EmbeddedId
    UserRelationshipId userRelationshipId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("userFirstId")
//    @JoinColumn(name="user_first_id")
//    @JsonIgnore
//    private User userFirst;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_second_id")
//    @MapsId("userSecondId")
//    @JsonIgnore
//    private User userSecond;

}
