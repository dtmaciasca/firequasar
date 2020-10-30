package com.mercadolibre.firequasar.service;

import com.mercadolibre.firequasar.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FireQuasarService {

    private SatelliteOperatorService satelliteOperatorBusiness;
    private SatelliteService satelliteService;
    private MessageReceivedService messageReceivedService;

    public FireQuasarService(SatelliteOperatorService satelliteOperatorBusiness, SatelliteService satelliteService, MessageReceivedService messageReceivedService) {
        this.satelliteOperatorBusiness = satelliteOperatorBusiness;
        this.satelliteService = satelliteService;
        this.messageReceivedService = messageReceivedService;
    }

    public SatelliteResponse getUbicationShapeAndMessage(SatelliteRequest satelliteRequest){
        try{
            return calculatePositionAndMessage(satelliteRequest.getSatellites());
        }catch (SatelliteOperatorService.SatelliteOperatorServiceException e){
            throw new FireQuasarServiceException(e.getMessage());
        }
    }

    public String saveDistanceAndMessageSatellite(String name, DistanceMessage distanceMessage){
        try {
            return messageReceivedService.create(satelliteService.findByName(name), distanceMessage);
        }catch (Exception e){
            throw new FireQuasarServiceException(e.getMessage());
        }
    }

    public SatelliteResponse getPositionAndMessage(){
        try{
            List<MessageReceived> messageReceivers = messageReceivedService.findAll();
            if(Objects.nonNull(messageReceivers) && !messageReceivers.isEmpty() && messageReceivers.size()>1) {
                List<SatelliteMessage> satelliteMessages = new ArrayList<>();
                messageReceivers.forEach(mr -> satelliteMessages.add(
                        SatelliteMessage.builder().name(mr.getSatellite().getName()).message(mr.getDistanceMessage().getMessage())
                                .distance(mr.getDistanceMessage().getDistance()).build()));
                return calculatePositionAndMessage(satelliteMessages);
            }
        }catch (Exception e){
            throw new FireQuasarServiceException(e.getMessage());
        }
        throw new NotInformationEnoughException("No hay información suficiente");
    }

    private SatelliteResponse calculatePositionAndMessage(List<SatelliteMessage> satelliteMessages){
        return SatelliteResponse.builder()
                .message(satelliteOperatorBusiness.getMessage(satelliteMessages))
                .position(satelliteOperatorBusiness.getLocation(satelliteMessages))
                .build();
    }

    public static class FireQuasarServiceException extends IllegalArgumentException {
        FireQuasarServiceException(String error) {
            super(error);
        }
    }

    public static class NotInformationEnoughException extends IllegalArgumentException {
        NotInformationEnoughException(String error) {
            super(error);
        }
    }

}
