package com.site.reon.aggregate.record.command.domain;

import com.site.reon.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "roasting_record")
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

    @Column(length = 20000)
    private String ror;

    @Column(length = 100, nullable = false)
    private String roasterSn;

    @Column(nullable = false)
    private long memberId;

    @Column(length = 100)
    private String crackPoint;

    @Column(length = 100)
    private String crackPointTime;

    @Column(length = 100)
    private String turningPointTemp;

    @Column(length = 100)
    private String turningPointTime;

    @Column
    private Float preheatTemp;

    @Embedded
    private Dispose dispose;

    @Embedded
    private CoolingPoint coolingPoint;

    @Column
    private Integer inputCapacity;

    @Column
    @ColumnDefault("false")
    private boolean pilot;

    public void sharePilot(final Boolean pilot) {
        this.pilot = pilot;
    }
}