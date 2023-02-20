package web.banking.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.banking.domain.User;
import web.banking.domain.UserRelationship;
import web.banking.domain.UserRelationshipId;

import javax.persistence.EntityManager;
//import static web.banking.domain.UserRelationship.
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendRepository {
    private  final EntityManager em;
//    JPAQueryFactory queryFactory;

    //   public UserRelationship findRelationship(Long userId1, Long userId2){
//        queryFactory = new JPAQueryFactory(em);
//        UserRelationship relationship = queryFactory.selectFrom()
//    }
    public boolean existsByUserRelationshipId(UserRelationshipId userRelationshipId){
        UserRelationship userRelationship = em.find(UserRelationship.class, userRelationshipId);
        return userRelationship != null;
    }

    public void save(UserRelationship userRelationship){
        em.persist(userRelationship);
    }

}
