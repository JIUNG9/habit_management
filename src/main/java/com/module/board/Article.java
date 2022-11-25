package com.module.board;

import com.module.user.User;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "creadted_at")
    private Timestamp createdAt;

    @Column(name = "is_popular_artile")
    private boolean isPopularArticle;

    @Column(name = "count_view")
    private int countView;

    @Column(name="content")
    @Lob
    private String content;

    @Column(name= "vote")
    private int vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Board board;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;




}
