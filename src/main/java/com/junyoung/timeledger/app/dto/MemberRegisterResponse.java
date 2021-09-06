package com.junyoung.timeledger.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class MemberRegisterResponse {

    private final Long id;
    private final String userId;
}
