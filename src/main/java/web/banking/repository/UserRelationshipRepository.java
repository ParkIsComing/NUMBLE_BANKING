package web.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.banking.domain.UserRelationship;
import web.banking.domain.UserRelationshipId;

public interface UserRelationshipRepository extends JpaRepository<UserRelationship, UserRelationshipId> {
    boolean existsByUserFirstIdAndUserSecondId(Long userFirstId, Long userSecondId);
}
