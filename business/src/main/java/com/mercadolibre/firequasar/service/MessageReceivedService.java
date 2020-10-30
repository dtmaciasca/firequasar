package com.mercadolibre.firequasar.service;

import com.mercadolibre.firequasar.jpa.MessageReceivedRepository;
import com.mercadolibre.firequasar.jpa.SatelliteRepository;
import com.mercadolibre.firequasar.model.DistanceMessage;
import com.mercadolibre.firequasar.model.MessageReceived;
import com.mercadolibre.firequasar.model.Satellite;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MessageReceivedService {

    private static final Logger LOGGER = Logger.getLogger(MessageReceivedService.class.getName());
    private MessageReceivedRepository messageReceivedRepository;

    public MessageReceivedService(MessageReceivedRepository messageReceivedRepository) {
        this.messageReceivedRepository = messageReceivedRepository;
    }

    public String create(Satellite satellite, DistanceMessage distanceMessage){
        try{
            MessageReceived messageReceived = findBySatellite(satellite);
            if(Objects.nonNull(messageReceived)){
                messageReceivedRepository.save(messageReceived.toBuilder().distanceMessage(distanceMessage).build());
                return MessageFormat.format("Actualizó la distancia y el mensaje para el satélite {0}", satellite.getName());
            }else{
                messageReceivedRepository.save(MessageReceived.builder().satellite(satellite).distanceMessage(distanceMessage).build());
                return MessageFormat.format("Agregó la distancia y el mensaje del satélite {0}", satellite.getName());
            }
        }catch (SatelliteRepository.NotFoundSatelliteException exception){
            throw new MessageReceivedServiceException(exception.getMessage());
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "Error creando el mensaje recibido: {0}", e.getMessage());
            throw new MessageReceivedServiceException("Error creando el mensaje recibido");
        }
    }

    public MessageReceived findBySatellite(Satellite satellite){
        return messageReceivedRepository.findBySatellite(satellite);
    }

    public List<MessageReceived> findAll(){
        return messageReceivedRepository.findAll();
    }

    public class MessageReceivedServiceException extends RuntimeException {
        public MessageReceivedServiceException(String error) {
            super(error);
        }
    }
}
