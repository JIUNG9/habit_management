package com.module.habit;

import com.module.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "habit_statics")
public class HabitStatics {

    @Id
    @Column(name="habit_statics_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="day_count_from_start")
    private Timestamp dayCount;

    @Column(name ="increase_or_decrease")
    @Enumerated(EnumType.STRING)
    private IncreaseOrDecrease increaseOrDecrease;

    @Column(name ="money_save", nullable = true)
    private Double moneySave;

    @Column(name ="time_save", nullable = true)
    private Timestamp timeSave;

    @Column(name ="life_expectancy_save", nullable = true)
    private Double lifeExpectancySave;

    @Column(name ="total_static" ,nullable = true)
    private Double totalStatic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Habit habit;



}
