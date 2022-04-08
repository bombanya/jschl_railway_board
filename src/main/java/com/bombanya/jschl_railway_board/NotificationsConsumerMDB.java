package com.bombanya.jschl_railway_board;

import com.bombanya.jschl_railway_board.entities.StationScheduleInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/testQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "user", propertyValue = "admin"),
        @ActivationConfigProperty(propertyName = "password", propertyValue = "admin"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
@ResourceAdapter("remote-artemis")
public class NotificationsConsumerMDB implements MessageListener {

    @Inject
    @Push(channel = "scheduleChannel")
    private PushContext pushContext;
    @EJB
    private ScheduleHolderBean scheduleHolderBean;
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public void onMessage(Message message) {
        try {
            int stationId = message.getIntProperty("stationId");
            if (scheduleHolderBean.interestedInStation(stationId)) {
                String msg = ((TextMessage) message).getText();
                StationScheduleInfo scheduleInfo = objectMapper.readValue(msg,
                        StationScheduleInfo.class);
                System.out.println(scheduleInfo);
                scheduleHolderBean.updateSchedule(stationId, scheduleInfo);
                pushContext.send("scheduleUpdate", stationId);
            }
        } catch (JMSException | JsonProcessingException e) {
            System.out.println("Error in MDB");
        }
    }
}
