package com.module.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 582462457L;

    public static final QUser user = new QUser("user");

    public final ListPath<com.module.board.Article, com.module.board.QArticle> articleList = this.<com.module.board.Article, com.module.board.QArticle>createList("articleList", com.module.board.Article.class, com.module.board.QArticle.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<com.module.mail.EmailToken, com.module.mail.QEmailToken> emailTokenList = this.<com.module.mail.EmailToken, com.module.mail.QEmailToken>createList("emailTokenList", com.module.mail.EmailToken.class, com.module.mail.QEmailToken.class, PathInits.DIRECT2);

    public final ListPath<com.module.habit.HabitStatics, com.module.habit.QHabitStatics> habitStaticsList = this.<com.module.habit.HabitStatics, com.module.habit.QHabitStatics>createList("habitStaticsList", com.module.habit.HabitStatics.class, com.module.habit.QHabitStatics.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<com.module.group.Room, com.module.group.QRoom> roomList = this.<com.module.group.Room, com.module.group.QRoom>createList("roomList", com.module.group.Room.class, com.module.group.QRoom.class, PathInits.DIRECT2);

    public final NumberPath<Integer> warningCount = createNumber("warningCount", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

