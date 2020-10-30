package com.mercadolibre.firequasar.service;

import com.mercadolibre.firequasar.jpa.SatelliteRepository;
import com.mercadolibre.firequasar.model.Position;
import com.mercadolibre.firequasar.model.Satellite;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SatelliteService {

    private static final Logger LOGGER = Logger.getLogger(SatelliteService.class.getName());
    private SatelliteRepository satelliteRepository;

    public SatelliteService(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    public double[][] findPositionByName(String[] names){
        return satelliteRepository
                .findPositionByName(names)
                .stream().map(Position::getPosition)
                .toArray(double[][]::new);
    }

    public Satellite findByName(String name){
        try{
            Satellite satellite = satelliteRepository.findByName(name);
            if(Objects.nonNull(satellite)){
                return satellite;
            }
            throw new SatelliteRepository.NotFoundSatelliteException(MessageFormat.format("El satélite {0} no existe", name));
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "Error consultando el satélite: {0}", e.getMessage());
            throw new SatelliteRepository.NotFoundSatelliteException(MessageFormat.format("El satélite {0} no existe", name));
        }
    }

}
