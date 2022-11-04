package com.module.habit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHabitStatics is a Querydsl query type for HabitStatics
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHabitStatics extends EntityPathBase<HabitStatics> {

    private static final long serialVersionUID = -1907473760L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHabitStatics habitStatics = new QHabitStatics("habitStatics");

    public final DateTimePath<java.sql.Timestamp> dayCount = createDateTime("dayCount", java.sql.Timestamp.class);

    public final QHabit habit;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<IncreaseOrDecrease> increaseOrDecrease = createEnum("increaseOrDecrease", IncreaseOrDecrease.class);

    public final NumberPath<Double> lifeExpectancySave = createNumber("lifeExpectancySave", Double.class);

    public final NumberPath<Double> moneySave = createNumber("moneySave", Double.class);

    public final DateTimePath<java.sql.Timestamp> timeSave = createDateTime("timeSave", java.sql.Timestamp.class);

    public final NumberPath<Double> totalStatic = createNumber("totalStatic", Double.class);

    public final com.module.user.QUser user;

    public QHabitStatics(String variable) {
        this(HabitStatics.class, forVariable(variable), INITS);
    }

    public QHabitStatics(Path<? extends HabitStatics> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHabitStatics(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHabitStatics(PathMetadata metadata, PathInits inits) {
        this(HabitStatics.class, metadata, inits);
    }

    public QHabitStatics(Class<? extends HabitStatics> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.habit = inits.isInitialized("habit") ? new QHabit(forProperty("habit"), inits.get("habit")) : null;
        this.user = inits.isInitialized("user") ? new com.module.user.QUser(forProperty("user")) : null;
    }

}

