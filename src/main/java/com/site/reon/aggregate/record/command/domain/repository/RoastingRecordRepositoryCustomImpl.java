package com.site.reon.aggregate.record.command.domain.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.site.reon.aggregate.record.command.domain.QRoastingRecord;
import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.query.dto.SearchRequestParam;
import com.site.reon.aggregate.record.util.RecordUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class RoastingRecordRepositoryCustomImpl implements RoastingRecordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<RoastingRecord> findByFilter(final Long memberId, final SearchRequestParam param) {
        final var pageable = PageRequest.of(param.getPage(), param.getSize(), Sort.by("id").descending());

        final List<RoastingRecord> content = getContent(memberId, param, pageable);
        final long count = getCount(memberId, param);

        return new PageImpl<>(content, pageable, count);
    }

    private List<RoastingRecord> getContent(
            final Long memberId, final SearchRequestParam param, final PageRequest pageable) {

        QRoastingRecord record = QRoastingRecord.roastingRecord;
        final JPAQuery<RoastingRecord> query = jpaQueryFactory.selectFrom(record)
                .where(record.roastingInfo.memberId.eq(memberId));

        if (StringUtils.isNotBlank(param.getTitle())) {
            query.where(record.roastingInfo.title.contains(param.getTitle()));
        }

        final String startDate = param.getStartDate();
        final String endDate = param.getEndDate();
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            LocalDate start = LocalDate.parse(startDate, RecordUtils.DATE_FORMATTER);
            LocalDate end = LocalDate.parse(endDate, RecordUtils.DATE_FORMATTER);
            query.where(record.createdDt.between(start.atStartOfDay(), end.atTime(LocalTime.of(23, 59, 59))));
        }

        return query
                .orderBy(record.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private long getCount(final Long memberId, final SearchRequestParam param) {
        QRoastingRecord record = QRoastingRecord.roastingRecord;
        final JPAQuery<Long> query = jpaQueryFactory.select(record.count())
                .from(record)
                .where(record.roastingInfo.memberId.eq(memberId));

        if (StringUtils.isNotBlank(param.getTitle())) {
            query.where(record.roastingInfo.title.contains(param.getTitle()));
        }

        final String startDate = param.getStartDate();
        final String endDate = param.getEndDate();
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            LocalDate start = LocalDate.parse(startDate, RecordUtils.DATE_FORMATTER);
            LocalDate end = LocalDate.parse(endDate, RecordUtils.DATE_FORMATTER);
            query.where(record.createdDt.between(start.atStartOfDay(), end.atTime(LocalTime.of(23, 59, 59))));
        }

        return query.fetchOne();
    }
}
