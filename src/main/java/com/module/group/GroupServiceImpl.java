package com.module.group;

import com.module.user.User;
import com.module.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserAndGroupRepository userAndGroupRepository;

    public void createGroup(String groupName, String userEmail){

        User user =Optional.ofNullable(userRepository.getUserByEmail(userEmail)).orElseThrow(IllegalAccessError::new);
        Group group =Group.builder().admin(user.getEmail()).name(groupName).build();
        Optional.ofNullable(groupRepository.save(group)).orElseThrow(IllegalArgumentException::new);
        UserGroup userGroup = UserGroup.builder().user(user).group(group).build();
        Optional.ofNullable(userAndGroupRepository.save(userGroup)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Group> getAll() {
        return Optional.ofNullable(groupRepository.findAll()).orElseThrow(EntityNotFoundException::new);

    }


}
