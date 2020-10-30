package com.mercadolibre.firequasar.model;

import java.util.Arrays;
import java.util.List;

public class SatelliteMother {

    private SatelliteMother(){};

    public static Satellite satelliteKenobi(){
        return Satellite.builder()
                .name("kenobi")
                .position(PositionMother.positionKenobi())
                .build();
    }

    public static Satellite satelliteSkywalker(){
        return Satellite.builder()
                .name("skywalker")
                .position(PositionMother.positionSkywalker())
                .build();
    }

    public static Satellite satelliteSato(){
        return Satellite.builder()
                .name("sato")
                .position(PositionMother.positionSato())
                .build();
    }

    public static Satellite satelliteNotExits(){
        return Satellite.builder()
                .name("soto")
                .position(PositionMother.positionOne())
                .build();
    }

    public static List<Satellite> all(){
        return Arrays.asList(SatelliteMother.satelliteKenobi(), satelliteSkywalker(), satelliteSato());
    }
}
