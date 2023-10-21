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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String fan;

    @Column(nullable = false)
    private String heater;

    @Column(nullable = false)
    private String temp1;

    @Column(nullable = false)
    private String temp2;

    @Column(nullable = false)
    private String temp3;

    @Column(nullable = false)
    private String temp4;

    @Column(nullable = false)
    private String roasterSn;

    @Column(nullable = false)
    private long userId;
}
