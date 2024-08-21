package com.site.reon.aggregate.workshop.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopSearchRequestParam {
    private int page = 0;
    private int size = 10;
}
