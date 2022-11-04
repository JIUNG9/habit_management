package com.module.habit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHabitCategory is a Querydsl query type for HabitCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHabitCategory extends EntityPathBase<HabitCategory> {

    private static final long serialVersionUID = -269953661L;

    public static final QHabitCategory habitCategory = new QHabitCategory("habitCategory");

    public final StringPath category = createString("category");

    public final ListPath<Habit, QHabit> habits = this.<Habit, QHabit>createList("habits", Habit.class, QHabit.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QHabitCategory(String variable) {
        super(HabitCategory.class, forVariable(variable));
    }

    public QHabitCategory(Path<? extends HabitCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHabitCategory(PathMetadata metadata) {
        super(HabitCategory.class, metadata);
    }

}

