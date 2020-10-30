package com.mercadolibre.firequasar.model;

public class DistanceMessageMother {

    private DistanceMessageMother(){};

    public static DistanceMessage distanceMessageOne(){
        return DistanceMessage.builder()
                .distance(123.9)
                .message(new String[]{"este","","","un"})
                .build();
    }

    public static DistanceMessage distanceMessageTwo(){
        return DistanceMessage.builder()
                .distance(15.9)
                .message(new String[]{"este","mensaje","",""})
                .build();
    }

    public static DistanceMessage distanceMessageKenobi(){
        return DistanceMessage.builder()
                .distance(100.0)
                .message(new String[]{"este", "", "", "mensaje", ""})
                .build();
    }

    public static DistanceMessage distanceMessageSkywalker(){
        return DistanceMessage.builder()
                .distance(115.5)
                .message(new String[]{"", "es", "", "", "secreto"})
                .build();
    }

    public static DistanceMessage distanceMessageSato(){
        return DistanceMessage.builder()
                .distance(15.9)
                .message(new String[]{"este", "", "un", "", ""})
                .build();
    }

}
