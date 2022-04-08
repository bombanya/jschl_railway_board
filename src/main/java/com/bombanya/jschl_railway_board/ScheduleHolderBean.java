package com.bombanya.jschl_railway_board;

import com.bombanya.jschl_railway_board.entities.Station;
import com.bombanya.jschl_railway_board.entities.StationScheduleInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.SneakyThrows;

import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ScheduleHolderBean {

    private final String serverUrl = "http://localhost:8080/javaschool_railway-1.0-SNAPSHOT";
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, StationScheduleInfo>> schedule =
            new ConcurrentHashMap<>();

    public Map<Integer, StationScheduleInfo> getStationSchedule(@NonNull Station station) {
        if (!schedule.containsKey(station.getId())) {
            ConcurrentHashMap<Integer, StationScheduleInfo> newSchedule = new ConcurrentHashMap<>();
            fetchSchedule(station).forEach(stationScheduleInfo ->
                    newSchedule.put(stationScheduleInfo.getRunId(), stationScheduleInfo));
            schedule.put(station.getId(), newSchedule);
        }
        return schedule.get(station.getId());
    }

    @SneakyThrows
    private List<StationScheduleInfo> fetchSchedule(Station station) {
        Client client = ClientBuilder.newClient();
        String response = client.target(serverUrl)
                .path("/api/schedule/public")
                .path("/" + station.getId())
                .path("/" + Instant.now().atZone(station.getSettlement().getTimeZone())
                        .toLocalDate())
                //.path("/2022-04-16")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        client.close();
        response = response
                .substring(response.indexOf('['), response.lastIndexOf(']') + 1);
        return objectMapper.readValue(response, new TypeReference<List<StationScheduleInfo>>() {});
    }

    public boolean interestedInStation(int stationId) {
        return schedule.containsKey(stationId);
    }

    public void updateSchedule(int stationId, StationScheduleInfo scheduleInfo) {
        if (!schedule.containsKey(stationId)) return;
        schedule.get(stationId).put(scheduleInfo.getRunId(), scheduleInfo);
    }
}
