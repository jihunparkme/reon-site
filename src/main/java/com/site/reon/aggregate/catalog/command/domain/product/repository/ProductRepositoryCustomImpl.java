package com.site.reon.aggregate.catalog.command.domain.product.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.site.reon.aggregate.catalog.command.domain.product.Product;
import com.site.reon.aggregate.catalog.command.domain.product.QProduct;
import com.site.reon.aggregate.catalog.query.dto.ProductSearchRequestParam;
import com.site.reon.aggregate.record.util.RecordUtils;
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
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> findAllByFilter(final ProductSearchRequestParam param) {
        final PageRequest pageable = PageRequest.of(param.getPage(), param.getSize(), Sort.by("id").descending());

        final List<Product> content = getContent(param, pageable);
        final long count = getCount(param);

        return new PageImpl<>(content, pageable, count);
    }

    private List<Product> getContent(final ProductSearchRequestParam param, final PageRequest pageable) {
        final QProduct product = QProduct.product;
        final JPAQuery<Product> query = jpaQueryFactory.selectFrom(product);

        applyWhereClause(param, query, product);

        return query
                .orderBy(product.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private long getCount(final ProductSearchRequestParam param) {
        final QProduct product = QProduct.product;
        final JPAQuery<Long> query = jpaQueryFactory.select(product.count())
                .from(product);

        applyWhereClause(param, query, product);

        return query.fetchOne();
    }

    private <T> void applyWhereClause(final ProductSearchRequestParam param,
                                      final JPAQuery<T> query,
                                      final QProduct product) {

        if (StringUtils.isNotEmpty(param.getModelName())) {
            query.where(product.productInfo.name.containsIgnoreCase(param.getModelName()));
        }

        if (StringUtils.isNotEmpty(param.getProductNo())) {
            query.where(product.productInfo.productNo.no.containsIgnoreCase(param.getProductNo()));
        }

        if (StringUtils.isNotEmpty(param.getSerialNo())) {
            query.where(product.productInfo.serialNo.no.containsIgnoreCase(param.getSerialNo()));
        }

        query.where(product.productInfo.serialNo.activated.eq(param.isActivated()));

        final String startDate = param.getStartDate();
        final String endDate = param.getEndDate();
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            LocalDate start = LocalDate.parse(startDate, RecordUtils.DATE_FORMATTER);
            LocalDate end = LocalDate.parse(endDate, RecordUtils.DATE_FORMATTER);
            query.where(product.productInfo.manufacturedDt.between(start.atStartOfDay(), end.atTime(LocalTime.of(23, 59, 59))));
        }
    }
}
