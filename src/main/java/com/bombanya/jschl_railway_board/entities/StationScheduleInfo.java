package com.bombanya.jschl_railway_board.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StationScheduleInfo {

    private int runId;
    private int trainId;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    private int finalStationId;
    private String finalStationName;
    private TrainStatus status;
}
