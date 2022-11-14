package com.module.chat;

import com.module.room.Room;
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

    @ManyToOne
    @JoinColumn
    private Room room;

}
