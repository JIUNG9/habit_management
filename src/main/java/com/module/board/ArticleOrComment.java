package com.module.board;

public enum ArticleOrComment {
    ARTICLE("Article"),
    Comment("Comment");

    private String articleOrComment;

    ArticleOrComment(String input) {
        this.articleOrComment=input;
    }

    public String getArticleOrComment(){
        return this.articleOrComment;
    }
}
