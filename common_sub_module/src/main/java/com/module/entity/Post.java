package com.module.entity;

import com.module.dto.PostDto;
import com.module.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
@Table(
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"post_title"})}
)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Column(name = "post_view_count", columnDefinition = "BIGINT default 0", nullable = false)
    private long viewCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    public void updatePost(Habit habit){
        this.habit = habit;
    }

    public void updatePostBody(PostDto postDto){
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
    }

    public void updateViewCount(){
        this.viewCount += 1;
    }

}
