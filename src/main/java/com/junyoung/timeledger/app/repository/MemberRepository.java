package com.junyoung.timeledger.app.repository;

import com.junyoung.timeledger.app.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return em.find(Member.class, member.getId());
    }

    public Member findByUserId(String userId) {
        try {
            return em.createQuery("select m from Member m where m.userId = :name", Member.class)
                    .setParameter("name", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

}
