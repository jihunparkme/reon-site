package com.site.reon.aggregate.member.command.domain.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.QMember;
import com.site.reon.aggregate.member.query.dto.MemberSearchRequestParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Member> findAllByFilter(final MemberSearchRequestParam param) {
        final var pageable = PageRequest.of(param.getPage(), param.getSize(), Sort.by("id").descending());

        final List<Member> content = getContent(param, pageable);
        final long count = getCount(param);

        return new PageImpl<>(content, pageable, count);
    }

    private List<Member> getContent(final MemberSearchRequestParam param, final PageRequest pageable) {
        final QMember member = QMember.member;
        final JPAQuery<Member> query = jpaQueryFactory.selectFrom(member);

        applyWhereClause(param, query, member);

        return query
                .orderBy(member.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private long getCount(final MemberSearchRequestParam param) {
        final QMember member = QMember.member;
        final JPAQuery<Long> query = jpaQueryFactory.select(member.count())
                .from(member);

        applyWhereClause(param, query, member);

        return query.fetchCount();
    }

    private <T> void applyWhereClause(final MemberSearchRequestParam param, final JPAQuery<T> query, final QMember member) {
        if (StringUtils.isNotEmpty(param.getName())) {
            query.where(member.personalInfo.firstName.contains(param.getName())
                    .or(member.personalInfo.lastName.contains(param.getName())));
        }

        if (StringUtils.isNotEmpty(param.getEmail())) {
            query.where(member.email.eq(param.getEmail()));
        }

        if (StringUtils.isNotEmpty(param.getSerialNo())) {
            query.where(member.productInfo.roasterSn.eq(param.getSerialNo()));
        }
    }
}
