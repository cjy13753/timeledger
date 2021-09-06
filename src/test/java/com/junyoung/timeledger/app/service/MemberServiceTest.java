package com.junyoung.timeledger.app.service;

import com.junyoung.timeledger.app.dto.MemberRegisterResponse;
import com.junyoung.timeledger.app.entity.Member;
import com.junyoung.timeledger.app.repository.MemberRepository;
import com.junyoung.timeledger.exception.MemberErrorResult;
import com.junyoung.timeledger.exception.MemberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService target;

    @Mock
    private MemberRepository memberRepository;

    private final String userId = "userId";
    private final String userPw = "userPw";

    @Test
    void Should_RegisterNewMember_When_NoDuplicateMemberExists(){
        // given
        doReturn(null).when(memberRepository).findByUserId(userId);
        doReturn(member()).when(memberRepository).save(any(Member.class));

        // when
        final MemberRegisterResponse result = target.registerAsNewMember(userId, userPw);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
    }
    
    @Test
    void Should_FailToRegisterNewMember_When_DuplicateMemberExists(){
        // given
        doReturn(member()).when(memberRepository).findByUserId(userId);

        // when
        final MemberException result = assertThrows(MemberException.class, () -> target.registerAsNewMember(userId, userPw));

        // then
        assertThat(result.getMemberErrorResult()).isEqualTo(MemberErrorResult.DUPLICATED_MEMBER_REGISTER);
    }

    private Member member() {
        return Member.builder()
                .id(-1L)
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}
