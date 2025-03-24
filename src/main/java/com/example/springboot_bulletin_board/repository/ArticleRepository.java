package com.example.springboot_bulletin_board.repository;

import com.example.springboot_bulletin_board.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> //<엔티티 클래스 타입, 엔티티 대표값(ID) 타입>
{
    @Override
    ArrayList<Article> findAll();
}
