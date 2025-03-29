package com.example.springboot_bulletin_board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor //JPA가 리플렉션으로 객체 생성하므로 필요
@ToString
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //DB가 id 자동 생성
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }
}
