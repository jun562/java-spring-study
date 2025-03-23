package com.example.springboot_bulletin_board.repository;

import com.example.springboot_bulletin_board.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
