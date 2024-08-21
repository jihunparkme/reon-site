package com.site.reon.aggregate.workshop.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "workshop")
@Getter
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 20000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Long recordId;
}
