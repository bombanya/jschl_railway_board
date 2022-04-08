package com.bombanya.jschl_railway_board.entities;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    private Integer id;
    private String name;
    private Country country;
}
