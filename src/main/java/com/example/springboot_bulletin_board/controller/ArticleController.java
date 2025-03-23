package com.example.springboot_bulletin_board.controller;
import com.example.springboot_bulletin_board.dto.ArticleForm;
import com.example.springboot_bulletin_board.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String  createArticle(ArticleForm form){
        System.out.println(form.toString());
//        1. DTO를 엔티티로 변환
        Article article = form.toEntity();
//        2. 리파지터리로 엔티티를 DB에 저장
        return "";
    }
}
