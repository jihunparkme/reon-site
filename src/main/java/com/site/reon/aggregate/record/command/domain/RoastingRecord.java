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

    @Embedded
    private Profile profile;

    @Column
    @ColumnDefault("false")
    private boolean pilot;

    public void sharePilot(final Boolean pilot) {
        this.pilot = pilot;
    }

    public void updateMeno(final String memo) {
        final RoastingInfo updatedRoastingInfo = RoastingInfo.builder()
                .title(this.roastingInfo.getTitle())
                .roasterSn(this.roastingInfo.getRoasterSn())
                .memberId(this.roastingInfo.getMemberId())
                .memo(memo)
                .build();
        this.roastingInfo = updatedRoastingInfo;
    }
}