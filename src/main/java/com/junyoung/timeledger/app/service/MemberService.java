package com.junyoung.timeledger.app.service;

import com.junyoung.timeledger.app.dto.MemberRegisterResponse;
import com.junyoung.timeledger.app.entity.Member;
import com.junyoung.timeledger.app.repository.MemberRepository;
import com.junyoung.timeledger.exception.MemberErrorResult;
import com.junyoung.timeledger.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberRegisterResponse registerAsNewMember(final String userId, final String userPw) {
        final Member result = memberRepository.findByUserId(userId);
        if (result != null) {
            throw new MemberException(MemberErrorResult.DUPLICATED_MEMBER_REGISTER);
        }

        final Member member = Member.builder()
                .userId(userId)
                .userPw(userPw)
                .build();

        final Member savedMember = memberRepository.save(member);

        return MemberRegisterResponse.builder()
                .id(savedMember.getId())
                .userId(savedMember.getUserId())
                .build();

    }

}
