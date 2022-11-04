package com.module.group;

import com.module.user.User;
import lombok.*;
import org.hibernate.annotations.Target;

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
