package com.site.reon.aggregate.workshop.command.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.site.reon.aggregate.member.command.domain.QMember;
import com.site.reon.aggregate.record.util.RecordUtils;
import com.site.reon.aggregate.workshop.query.dto.WorkshopSearchRequestParam;
import com.site.reon.aggregate.workshop.query.dto.WorkshopsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkshopRepositoryCustomImpl implements WorkshopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<WorkshopsResponse> findAllByFilter(final WorkshopSearchRequestParam param) {
        final var pageable = PageRequest.of(param.getPage(), param.getSize(), Sort.by("id").descending());

        final List<WorkshopsResponse> content = getContent(param, pageable);
        final long count = getCount(param);

        return new PageImpl<>(content, pageable, count);
    }

    private List<WorkshopsResponse> getContent(final WorkshopSearchRequestParam param, final PageRequest pageable) {
        final QWorkshop workshop = QWorkshop.workshop;
        QMember member = QMember.member;
        final JPAQuery<WorkshopsResponse> query = jpaQueryFactory
                .select(Projections.constructor(WorkshopsResponse.class,
                        workshop.id,
                        workshop.title,
                        workshop.createdDt,
                        member.email,
                        member.personalInfo.firstName,
                        member.personalInfo.lastName
                        ))
                .join(workshop)
                .join(member)
                .on(workshop.memberId.eq(member.id));

        applyWhereClause(param, query, workshop, member);

        return query
                .orderBy(workshop.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private long getCount(final WorkshopSearchRequestParam param) {
        final QWorkshop workshop = QWorkshop.workshop;
        QMember member = QMember.member;
        final JPAQuery<Long> query = jpaQueryFactory
                .select(workshop.count())
                .join(workshop)
                .join(member)
                .on(workshop.memberId.eq(member.id));

        applyWhereClause(param, query, workshop, member);

        return query.fetchOne();
    }

    private <T> void applyWhereClause(final WorkshopSearchRequestParam param,
                                      final JPAQuery<T> query,
                                      final QWorkshop workshop,
                                      final QMember member) {

        if (StringUtils.isNotEmpty(param.getTitle())) {
            query.where(workshop.title.containsIgnoreCase(param.getTitle()));
        }

        if (StringUtils.isNotEmpty(param.getName())) {
            query.where(member.personalInfo.firstName.containsIgnoreCase(param.getName())
                    .or(member.personalInfo.lastName.containsIgnoreCase(param.getName())));
        }

        final String startDate = param.getStartDate();
        final String endDate = param.getEndDate();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            LocalDate start = LocalDate.parse(startDate, RecordUtils.DATE_FORMATTER);
            LocalDate end = LocalDate.parse(endDate, RecordUtils.DATE_FORMATTER);
            query.where(workshop.createdDt.between(start.atStartOfDay(), end.plusDays(1L).atStartOfDay()));
        }
    }
}
