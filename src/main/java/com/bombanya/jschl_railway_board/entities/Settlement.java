package com.bombanya.jschl_railway_board.entities;

import lombok.*;
import java.time.ZoneId;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Settlement {

    private Integer id;
    private String name;
    private Region region;
    private ZoneId timeZone;
}
