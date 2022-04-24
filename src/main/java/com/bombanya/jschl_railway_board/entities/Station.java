package com.bombanya.jschl_railway_board.entities;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    private Integer id;
    private String name;
    private Settlement settlement;
}
