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

    @Embedded
    private RoastingInfo roastingInfo;

    @Embedded
    private InputInfo inputInfo;

    @Column(length = 20000, nullable = false)
    private String fan;

    @Column(length = 20000, nullable = false)
    private String heater;

    @Embedded
    private Temperature temperature;

    @Column(length = 20000)
    private String ror;

    @Embedded
    private CrackPoint crackPoint;

    @Column(length = 100)
    private String turningPointTemp;

    @Column(length = 100)
    private String turningPointTime;

    @Embedded
    private Dispose dispose;

    @Embedded
    private CoolingPoint coolingPoint;

    @Column
    @ColumnDefault("false")
    private boolean pilot;

    public void sharePilot(final Boolean pilot) {
        this.pilot = pilot;
    }
}