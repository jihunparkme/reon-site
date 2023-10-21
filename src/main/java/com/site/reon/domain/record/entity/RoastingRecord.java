package com.site.reon.domain.record.entity;

import com.site.reon.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoastingRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 20000, nullable = false)
    private String fan;

    @Column(length = 20000, nullable = false)
    private String heater;

    @Column(length = 20000, nullable = false)
    private String temp1;

    @Column(length = 20000, nullable = false)
    private String temp2;

    @Column(length = 20000, nullable = false)
    private String temp3;

    @Column(length = 20000, nullable = false)
    private String temp4;

    @Column(length = 100, nullable = false)
    private String roasterSn;

    @Column(nullable = false)
    private long memberId;
}
