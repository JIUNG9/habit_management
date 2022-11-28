package com.module.repository;

import com.module.entity.HabitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HabitCategoryRepository extends JpaRepository<HabitCategory, Long> {

    @Query("SELECT c FROM HabitCategory c WHERE c.habitCategoryName = :habit_category_name")
    HabitCategory findByCategoryName(@Param("habit_category_name") String habit_category_name);
}
