package com.module.entity;

import com.module.entity.Chat;
import com.module.entity.Group;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Chat> chatList;

    @OneToOne(mappedBy = "room")
    private Group group;


}
