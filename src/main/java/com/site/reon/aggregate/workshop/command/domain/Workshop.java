package com.site.reon.aggregate.workshop.command.domain;

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
