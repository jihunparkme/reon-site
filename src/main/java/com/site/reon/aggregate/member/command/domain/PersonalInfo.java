package com.site.reon.aggregate.member.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonalInfo {
    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(length = 30)
    private String companyName;

    @Column(length = 100)
    private String address;

    public void updateFirstName(final String name) {
        this.firstName = name;
    }
}
