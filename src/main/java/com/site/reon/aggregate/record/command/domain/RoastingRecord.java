package com.site.reon.aggregate.record.command.domain;

import com.site.reon.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
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

    @Embedded
    private Profile profile;

    @Column
    @ColumnDefault("false")
    private boolean pilot;

    @Column
    private Long originalRecordId;

    public void sharePilot(final Boolean pilot) {
        this.pilot = pilot;
    }

    public void updateMemo(final String memo) {
        this.roastingInfo.updateMemo(memo);
    }
}