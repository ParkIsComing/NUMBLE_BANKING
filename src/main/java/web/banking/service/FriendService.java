package web.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.banking.domain.User;
import web.banking.domain.UserRelationship;
import web.banking.domain.UserRelationshipId;
import web.banking.exception.NotFoundMemberException;
import web.banking.exception.UserRelationshipAlreadyExistsException;
import web.banking.repository.FriendRepository;
import web.banking.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(Long userId, Long friendId){
        Long id1, id2;
        if(userId<friendId){
            id1=userId;
            id2=friendId;
        }else{
            id1=friendId;
            id2=userId;
        }

        UserRelationshipId userRelationshipId = new UserRelationshipId();
        userRelationshipId.setUserFirstId(id1);
        userRelationshipId.setUserSecondId(id2);

        if(friendRepository.existsByUserRelationshipId(userRelationshipId)){
            throw new UserRelationshipAlreadyExistsException("user relationship already exists.");
        }

        UserRelationship userRelationship = new UserRelationship();
        userRelationship.setUserRelationshipId(userRelationshipId);
        friendRepository.save(userRelationship);






    }


}
