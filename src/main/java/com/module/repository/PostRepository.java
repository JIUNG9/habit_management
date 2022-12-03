package com.module.repository;

import com.module.entity.Habit;
import com.module.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByHabitIn(List<Habit> habits, Pageable pageable);
}
