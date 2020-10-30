package com.mercadolibre.firequasar.model;

import java.util.Arrays;
import java.util.List;

public class MessageReceivedMother {

    private MessageReceivedMother(){};

    public static MessageReceived messageReceivedKenobi(){
        return MessageReceived.builder()
                .satellite(SatelliteMother.satelliteKenobi())
                .distanceMessage(DistanceMessageMother.distanceMessageKenobi())
                .build();
    }

    public static MessageReceived messageReceivedSkywalker(){
        return MessageReceived.builder()
                .satellite(SatelliteMother.satelliteSkywalker())
                .distanceMessage(DistanceMessageMother.distanceMessageSkywalker())
                .build();
    }

    public static MessageReceived messageReceivedSato(){
        return MessageReceived.builder()
                .satellite(SatelliteMother.satelliteSato())
                .distanceMessage(DistanceMessageMother.distanceMessageSato())
                .build();
    }

    public static List<MessageReceived> all(){
        return Arrays.asList(messageReceivedKenobi(), messageReceivedSkywalker(), messageReceivedSato());
    }
}
