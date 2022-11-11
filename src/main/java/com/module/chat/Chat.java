package com.module.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name ="type", columnDefinition = "text" )
    private String type;

    @OneToOne( mappedBy = "chat", cascade = CascadeType.ALL, targetEntity = Room.class, orphanRemoval = true)
    private Room room;

}
