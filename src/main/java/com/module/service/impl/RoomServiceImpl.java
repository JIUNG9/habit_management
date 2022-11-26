package com.module.service.impl;

import com.module.entity.Group;
import com.module.entity.Room;
import com.module.repository.GroupRepository;
import com.module.service.RoomService;
import com.module.utils.lambda.BindParameterSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final GroupRepository groupRepository;

    @Override
    public Room findRoomByGroup(Group group) {

        return Optional.
                    ofNullable(
                            groupRepository.findRoomByGroupId(group.getId())).
                                orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no room with that group"));

    }
}
