package com.bombanya.jschl_railway_board;

import com.bombanya.jschl_railway_board.entities.Station;
import com.bombanya.jschl_railway_board.entities.StationScheduleInfo;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named("indexBean")
@ViewScoped
public class IndexBackingBean implements Serializable {

    @EJB @Getter
    private StationsHolderBean stationsBean;
    @EJB
    private ScheduleHolderBean scheduleHolderBean;
    @Getter @Setter
    private Integer stationId;
    @Getter
    private Collection<StationScheduleInfo> stationSchedule;

    @PostConstruct
    private void init() {
        updateSchedule(stationsBean.getStations().get(0).getId());
        stationId = stationsBean.getStations().get(0).getId();
    }

    public String getLabelFromStation(Station station) {
        return station.getName() + ", " + station.getSettlement().getName() + ", " +
                station.getSettlement().getRegion().getName() + ", " +
                station.getSettlement().getRegion().getCountry().getName();
    }

    public void handleStationChange(ValueChangeEvent event) {
        updateSchedule((Integer) event.getNewValue());
    }

    private void updateSchedule(int stationId) {
        Station newStation = stationsBean.getStations()
                .stream()
                .filter(station -> station
                        .getId()
                        .equals(stationId))
                .findFirst()
                .get();
        stationSchedule = scheduleHolderBean.getStationSchedule(newStation).values();
        //stationSchedule.forEach(System.out::println);
    }
}
