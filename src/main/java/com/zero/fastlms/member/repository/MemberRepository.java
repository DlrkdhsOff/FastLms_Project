package com.zero.fastlms.member.repository;

import com.zero.fastlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmailAuthKey(String emailAuthKey);
}