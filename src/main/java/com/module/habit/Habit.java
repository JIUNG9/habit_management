package com.module.habit;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="start_time")
    private Timestamp startTime;

    @Column(name ="done_habit")
    private boolean done_habit;

    @Column(name ="progression_user_decided")
    private int progressUserDecided;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private HabitCategory habitCategory;

    @OneToMany(mappedBy = "habit")
    private List<HabitStatics> habitStaticsList;






}
