package com.site.reon.aggregate.member.domain;

import com.site.reon.aggregate.member.service.dto.MemberEditRequest;
import com.site.reon.global.common.BaseTimeEntity;
import com.site.reon.global.common.constant.member.MemberType;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
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

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 30)
    private String companyName;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String prdCode;

    @Column(length = 100)
    private String roasterSn;

    @Column(length = 2000)
    private String picture;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private OAuth2Client oAuthClient;

    private Long oauthUserId;

    @Column
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    public Member updateOAuth2User(final Long oAuthUserId, final String name, final String picture) {
        this.oauthUserId = oAuthUserId;
        this.firstName = name;
        this.picture = picture;
        return this;
    }

    public void update(MemberEditRequest memberEditRequest) {
        this.type = memberEditRequest.getType();
        this.firstName = memberEditRequest.getFirstName();
        this.lastName = memberEditRequest.getLastName();
        this.phone = memberEditRequest.getPhone();
        this.prdCode = memberEditRequest.getPrdCode();
        this.roasterSn = memberEditRequest.getRoasterSn();
        this.companyName = memberEditRequest.getCompanyName();
        this.address = memberEditRequest.getAddress();
    }
}