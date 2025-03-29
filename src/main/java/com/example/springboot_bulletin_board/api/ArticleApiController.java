package com.example.springboot_bulletin_board.api;

import com.example.springboot_bulletin_board.dto.ArticleForm;
import com.example.springboot_bulletin_board.entity.Article;
import com.example.springboot_bulletin_board.repository.ArticleRepository;
import com.example.springboot_bulletin_board.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

//    GET
    @GetMapping("/api/articles")
    public List<Article> index() { //REST 컨트롤러는 데이터를 반환
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) { //REST 컨트롤러는 데이터를 반환
        return articleService.show(id);
    }
//    POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
//        데이터에 상태코드를 실어 보내기 위해 ResponseEntity 클래스 사용
        Article updated = articleService.update(id, dto); //서비스를 통해 게시글 수정
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
