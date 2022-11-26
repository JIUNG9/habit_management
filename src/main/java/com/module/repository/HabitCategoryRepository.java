package com.module.repository;

import com.module.entity.HabitCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitCategoryRepository extends JpaRepository<HabitCategory, Long> {

}
