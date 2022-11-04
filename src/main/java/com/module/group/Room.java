package com.module.group;

import com.module.user.User;
import lombok.*;

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





}
