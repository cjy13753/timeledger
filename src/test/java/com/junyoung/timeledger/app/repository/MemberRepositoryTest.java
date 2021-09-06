package com.junyoung.timeledger.app.repository;

import com.junyoung.timeledger.app.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private final String userId = "userId";
    private final String userPw = "userPw";

    @Test
    void Should_EnrollNewMember(){
        // given
        final Member member = member();

        // when
        final Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getUserPw()).isEqualTo(userPw);
    }

    @Test
    void Should_FindMember_With_UserId_When_Exists(){
        // given
        final Member savedMember = memberRepository.save(member());

        // when
        final Member foundMember = memberRepository.findByUserId(savedMember.getUserId());

        // then
        assertThat(foundMember.getId()).isNotNull();
        assertThat(foundMember.getUserId()).isEqualTo(userId);
        assertThat(foundMember.getUserPw()).isEqualTo(userPw);
    }
    
    @Test
    void Should_FailToFindMember_With_UserId_When_NotExists(){
        // given nothing

        // when
        final Member foundMember = memberRepository.findByUserId(userId);

        // then
        assertThat(foundMember).isNull();
    }

    private Member member() {
        return Member.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}
