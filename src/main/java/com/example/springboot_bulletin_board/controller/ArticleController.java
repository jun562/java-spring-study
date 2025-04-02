package com.example.springboot_bulletin_board.controller;
import com.example.springboot_bulletin_board.dto.ArticleForm;
import com.example.springboot_bulletin_board.dto.CommentDto;
import com.example.springboot_bulletin_board.entity.Article;
import com.example.springboot_bulletin_board.repository.ArticleRepository;
import com.example.springboot_bulletin_board.repository.CommentRepository;
import com.example.springboot_bulletin_board.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j //롬복 로깅
@Controller
public class ArticleController {
    @Autowired // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new") //HTTP 메소드
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String  createArticle(ArticleForm form){
        log.info(form.toString());
//        System.out.println(form.toString());
//        1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString()); //DTO -> 엔티티 변환 확인
//        2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString()); //article -> DB 저장 확인
        return "redirect:/articles/"+ saved.getId();
    }
    @GetMapping("/articles/{id}") //URL에 직접 id 입력하여 요청
    public String show(@PathVariable Long id, Model model){
        log.info("id " + id);
//        1. id를 조회해 DB에서 해당 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); //Optional은 NPE을 피하기 위해 사용
        List<CommentDto> commentDtos = commentService.comments(id);
//        2. 가져온 데이터를 모델에 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
//        3. 뷰 페이지 반환하기
        return "articles/show"; //항상 상대 경로는 templates의 하위 디렉토리
    }
    @GetMapping("/articles")
    public String index(Model model){
//        1. 모든 데이터 가져오기
        List<Article> articleEntityList =(List<Article>)articleRepository.findAll(); //findAll 반환 타입은 Iterable
//        2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
//        3. 뷰 페이지 설정하기
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit") //뷰페이지 변수 중괄호 2개, 컨트롤러 url 변수 중괄호 1개
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){ //매개변수로 DTO 받아옴
        log.info(form.toString());
//        1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
//        2. 엔티티를 DB에 저장하기
//        2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
//        2-2. 기존 데이터 값을 갱신하기
        if(target != null){
            articleRepository.save(articleEntity);
        }
//        3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/"+ articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
//        1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
//        2. 대상 엔티티 삭제하기
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
//        3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
