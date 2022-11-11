package com.module.chat;

import com.module.group.Group;
import com.module.group.UserGroup;
import com.module.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToOne
    @JoinColumn
    private Chat chat;


    //update the OneToOne
    @OneToOne(orphanRemoval = true)
    @JoinTable(name="id")
    private Group group;


}
