package com.example.springboot_bulletin_board.service;

import com.example.springboot_bulletin_board.dto.CommentDto;
import com.example.springboot_bulletin_board.entity.Article;
import com.example.springboot_bulletin_board.entity.Comment;
import com.example.springboot_bulletin_board.repository.ArticleRepository;
import com.example.springboot_bulletin_board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {

    /*
//        1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        2. 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        3. 결과 반환
       for(Comment comment : comments) {
            CommentDto dto = CommentDto.createCommentDto(comment);
            dtos.add(dto);
        }
    */
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
//        1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패! " + "대상 게시글이 없습니다."));
//        2. 댓글 엔티티 생성
        Comment comment = Comment.create(dto, article);
//        3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
//        4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
//        1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! "+"대상 댓글이 없습니다."));
//        2. 댓글 수정
        target.patch(dto);
//        3. DB로 갱신
        Comment updated = commentRepository.save(target);
//        4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
//        1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! "+"대상이 없습니다."));
//        2. 댓글 삭제
        commentRepository.delete(target);
//        3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
