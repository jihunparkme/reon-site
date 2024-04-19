package com.site.reon.global;

import com.google.common.base.CaseFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DatabaseCleanup implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;
    private final List<String> authorityTableNames = Arrays.asList("authority");
    private final Map<String, CleanupTable> tableIds = Map.of(
            "member", CleanupTable.MEMBER,
            "member_authority", CleanupTable.MEMBER_AUTHORITY,
            "product", CleanupTable.PRODUCT,
            "category", CleanupTable.CATEGORY
    );

    @Override
    public void afterPropertiesSet() {
        // JPA 모든 Entity 조회
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        // @Entity, @Table 선언된 엔티티의 테이블 이름을 가져와서 리스트에 담기
        tableNames = entities.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .map(e -> {
                    String tableName = e.getJavaType().getAnnotation(Table.class).name();
                    return tableName.isBlank() ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()) : tableName;
                })
                .collect(Collectors.toList());

        // @Entity 선언이 되어있지만 @Table 선언이 되어있지 않는 엔티티는 UPPER_CAMEL -> LOWER_UNDERSCORE 변환
        final List<String> entityNames = entities.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .toList();

        tableNames.addAll(entityNames);
    }

    private boolean isEntity(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Entity.class);
    }

    private boolean hasTableAnnotation(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Table.class);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            columnIdRestart(tableName);
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void columnIdRestart(final String tableName) {
        if (authorityTableNames.contains(tableName)) {
            entityManager.createNativeQuery("insert into authority (authority_name) values ('ROLE_GUEST')").executeUpdate();
            entityManager.createNativeQuery("insert into authority (authority_name) values ('ROLE_USER')").executeUpdate();
            entityManager.createNativeQuery("insert into authority (authority_name) values ('ROLE_ADMIN')").executeUpdate();
            entityManager.createNativeQuery("insert into authority (authority_name) values ('ROLE_PILOT')").executeUpdate();
            entityManager.createNativeQuery("delete from member_authority").executeUpdate();
            return;
        }

        final CleanupTable cleanupTable = tableIds.get(tableName);
        if (cleanupTable == null) {
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
            return;
        }

        entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " + cleanupTable.primaryKey() + " RESTART WITH " + cleanupTable.index()).executeUpdate();
    }
}