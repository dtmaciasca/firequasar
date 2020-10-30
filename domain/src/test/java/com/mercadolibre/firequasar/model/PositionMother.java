package com.mercadolibre.firequasar.model;

import java.util.Arrays;
import java.util.List;

public class PositionMother {

    private PositionMother (){};

    public static Position positionOne(){
        return Position.builder()
                .x(-487.28591249999994)
                .y(1557.0142249999997)
                .build();
    }

    public static Position positionTwo(){
        return Position.builder()
                .x(82.72784999999999)
                .y(-200)
                .build();
    }

    public static Position positionKenobi(){
        return Position.builder()
                .x(-500)
                .y(-200)
                .build();
    }

    public static Position positionSkywalker(){
        return Position.builder()
                .x(100)
                .y(-100)
                .build();
    }

    public static Position positionSato(){
        return Position.builder()
                .x(500)
                .y(100)
                .build();
    }

    public static List<Position> allPositionSatellites(){
        return Arrays.asList(positionKenobi(), positionSkywalker(), positionSato());
    }
}
