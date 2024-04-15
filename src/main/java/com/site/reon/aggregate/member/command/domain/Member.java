package com.site.reon.aggregate.member.command.domain;

import com.site.reon.aggregate.member.command.dto.MemberEditRequest;
import com.site.reon.global.common.BaseTimeEntity;
import com.site.reon.global.common.constant.member.MemberType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email_oAuthClient_unique",
                        columnNames = {"email", "oAuthClient"}
                )
        }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MemberType type;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Embedded
    private PersonalInfo personalInfo;

    @Embedded
    private ProductInfo productInfo;

    @Embedded
    private OAuth2 oAuth2;

    @Column
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    public Member updateOAuth2AccountInfo(final Long oAuthUserId, final String name, final String picture) {
        this.oAuth2.update(picture, oAuthUserId);
        this.personalInfo.updateFirstName(name);
        return this;
    }

    public void update(MemberEditRequest memberEditRequest) {
        this.type = memberEditRequest.getType();
        this.personalInfo = PersonalInfo.builder()
                .firstName(memberEditRequest.getFirstName())
                .lastName(memberEditRequest.getLastName())
                .phone(memberEditRequest.getPhone())
                .companyName(memberEditRequest.getCompanyName())
                .address(memberEditRequest.getAddress())
                .build();
        this.productInfo = ProductInfo.builder()
                .prdCode(memberEditRequest.getPrdCode())
                .roasterSn(memberEditRequest.getRoasterSn())
                .build();
    }
}