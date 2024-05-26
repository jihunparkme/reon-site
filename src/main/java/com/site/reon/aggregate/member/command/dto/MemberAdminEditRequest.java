package com.site.reon.aggregate.member.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAdminEditRequest extends MemberEditRequest {
    private long id;
}
