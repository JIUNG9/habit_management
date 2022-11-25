package com.module.board;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Article> articleList;



}
