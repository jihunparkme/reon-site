package com.site.reon.aggregate.workshop.command.domain;

import com.site.reon.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workshop")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workshop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 20000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Long recordId;

    @Column(nullable = false)
    private long memberId;
}
