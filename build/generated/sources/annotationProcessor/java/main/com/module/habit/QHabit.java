package com.module.habit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHabit is a Querydsl query type for Habit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHabit extends EntityPathBase<Habit> {

    private static final long serialVersionUID = 1694820709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHabit habit = new QHabit("habit");

    public final BooleanPath done_habit = createBoolean("done_habit");

    public final QHabitCategory habitCategory;

    public final ListPath<HabitStatics, QHabitStatics> habitStaticsList = this.<HabitStatics, QHabitStatics>createList("habitStaticsList", HabitStatics.class, QHabitStatics.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> progressUserDecided = createNumber("progressUserDecided", Integer.class);

    public final DateTimePath<java.sql.Timestamp> startTime = createDateTime("startTime", java.sql.Timestamp.class);

    public QHabit(String variable) {
        this(Habit.class, forVariable(variable), INITS);
    }

    public QHabit(Path<? extends Habit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHabit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHabit(PathMetadata metadata, PathInits inits) {
        this(Habit.class, metadata, inits);
    }

    public QHabit(Class<? extends Habit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.habitCategory = inits.isInitialized("habitCategory") ? new QHabitCategory(forProperty("habitCategory")) : null;
    }

}

