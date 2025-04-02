package com.example.springboot_bulletin_board.service;

import com.example.springboot_bulletin_board.dto.CommentDto;
import com.example.springboot_bulletin_board.entity.Comment;
import com.example.springboot_bulletin_board.repository.ArticleRepository;
import com.example.springboot_bulletin_board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
