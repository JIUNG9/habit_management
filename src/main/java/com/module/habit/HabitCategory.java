package com.module.habit;

import lombok.*;

import javax.persistence.*;
import java.util.List;



@RequiredArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "habit_category")
public class HabitCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="habit_category_id")
    private Integer id;

    @OneToMany(mappedBy = "habitCategory")
    private List<Habit> habits;

    @Column(name ="category")
    private String category;

}
