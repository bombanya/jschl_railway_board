package com.bombanya.jschl_railway_board;

import com.bombanya.jschl_railway_board.entities.Station;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("indexBean")
@ViewScoped
public class IndexBackingBean implements Serializable {

    @EJB @Getter
    private StationsHolderBean stationsBean;

    @Getter @Setter
    private Integer stationId;

    public String getLabelFromStation(Station station) {
        return station.getName() + ", " + station.getSettlement().getName() + ", " +
                station.getSettlement().getRegion().getName() + ", " +
                station.getSettlement().getRegion().getCountry().getName();
    }

    public void handleEvent(ValueChangeEvent event) {
        System.out.println(event.getNewValue());
    }
}
