package com.mercadolibre.firequasar.service;

import com.lemmingapex.trilateration.LinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.mercadolibre.firequasar.model.Position;
import com.mercadolibre.firequasar.model.SatelliteMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SatelliteOperatorService {

    private static final Logger LOGGER = Logger.getLogger(SatelliteOperatorService.class.getName());
    private static final String MESSAGE_LOG = "Error al interpretar la localización: {0}";

    private SatelliteService satelliteService;

    public SatelliteOperatorService(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    public Position getLocation(List<SatelliteMessage> satelliteMessages){
        try {
            TrilaterationFunction trilaterationFunction = new TrilaterationFunction(
                    satelliteService.findPositionByName(satelliteMessages.stream().map(SatelliteMessage::getName).toArray(String[]::new)),
                    satelliteMessages.stream().mapToDouble(SatelliteMessage::getDistance).toArray());
            LinearLeastSquaresSolver lSolver = new LinearLeastSquaresSolver(trilaterationFunction);
            double[] location = lSolver.solve().toArray();
            return Position.builder()
                    .x(location[0])
                    .y(location[1])
                    .build();
        } catch (IllegalArgumentException ex){
            if(satelliteMessages.size()>1){
                throw new SatelliteOperatorServiceException("Los satélites en el mensaje no existen", MESSAGE_LOG);
            }
            throw new SatelliteOperatorServiceException("Se necesita mínimo 2 y máximo 3 posiciones de los satélites para calcular la distancia", MESSAGE_LOG);
        } catch (Exception e){
            throw new SatelliteOperatorServiceException(e.getMessage(), MESSAGE_LOG);
        }
    }

    public String getMessage(List<SatelliteMessage> satellites){
        try {
            List<List<String>> message = new ArrayList<>();
            satellites.forEach(s -> {
                for (int i = 0; i < s.getMessage().length; i++) {
                    if (message.size() < i + 1) {
                        message.add(new ArrayList<>());
                    }
                    String word = s.getMessage()[i].trim();
                    if (!word.isEmpty() && message.get(i).stream().noneMatch(m -> m.trim().equals(word))
                            && (message.get(i).isEmpty() || (!(message.get(i).size()>1 && message.get(i-1).get(message.get(i-1).size()-1).trim().equalsIgnoreCase(word))))){
                        message.get(i).add(s.getMessage()[i]+" ");
                    }
                }
            });

            List<String> v = new ArrayList<>();
            message.forEach(
                    ms -> v.add(ms.stream().map(Object::toString).collect(Collectors.joining())));
            return v.stream().map(Object::toString).collect(Collectors.joining())
                    .trim();
        }catch (Exception e){
            throw new SatelliteOperatorServiceException(e.getMessage(), "Error al interpretar el mensaje: {0}");
        }
    }

    public static class SatelliteOperatorServiceException extends IllegalArgumentException {
        SatelliteOperatorServiceException(String error, String log) {
            super(error);
            LOGGER.log(Level.SEVERE, log, error);
        }
    }

}
