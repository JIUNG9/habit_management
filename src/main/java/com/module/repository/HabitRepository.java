package com.module.repository;

import com.module.entity.Habit;
import com.module.entity.HabitCategory;
import com.module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByHabitCategoryId(long habitCategoryId);

    List<Habit> findByUserId(long userId);

    Habit findByUserAndHabitCategory(User user, HabitCategory habitCategory);

}
