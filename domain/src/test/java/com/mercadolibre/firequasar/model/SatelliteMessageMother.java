package com.mercadolibre.firequasar.model;

import java.util.Arrays;
import java.util.List;

public class SatelliteMessageMother {

    private SatelliteMessageMother (){};

    public static SatelliteMessage satelliteMessageKenobi(){
        return SatelliteMessage.builder()
                .name("kenobi")
                .distance(100.0)
                .message(new String[]{"este", "", "", "mensaje", ""})
                .build();
    }

    public static SatelliteMessage satelliteMessageSkywalker(){
        return SatelliteMessage.builder()
                .name("skywalker")
                .distance(115.5)
                .message(new String[]{"", "es", "", "", "secreto"})
                .build();
    }

    public static SatelliteMessage satelliteMessageSato(){
        return SatelliteMessage.builder()
                .name("sato")
                .distance(142.7)
                .message(new String[]{"este", "", "un", "", ""})
                .build();
    }

    public static SatelliteMessage satelliteMessageKenobiTwo(){
        return SatelliteMessage.builder()
                .name("kenobi")
                .distance(100.0)
                .message(new String[]{"este", "", "", "mensaje", ""})
                .build();
    }

    public static SatelliteMessage satelliteMessageSkywalkerTwo(){
        return SatelliteMessage.builder()
                .name("skywalker")
                .distance(115.5)
                .message(new String[]{"", "es", "", "", "secreto"})
                .build();
    }

    public static SatelliteMessage satelliteMessageSatoTwo(){
        return SatelliteMessage.builder()
                .name("sato")
                .distance(142.7)
                .message(new String[]{"", "este", "un", "", "mensaje"})
                .build();
    }

    public static SatelliteMessage satelliteMessageWithSatelliteNotExist(){
        return SatelliteMessage.builder()
                .name("soto")
                .distance(125)
                .message(new String[]{"", ""})
                .build();
    }

    public static List<SatelliteMessage> allSatellitesExist(){
        return Arrays.asList(satelliteMessageKenobi(), satelliteMessageSkywalker(), satelliteMessageSato());
    }

    public static List<SatelliteMessage> all(){
        return Arrays.asList(satelliteMessageKenobi(), satelliteMessageSkywalker(), satelliteMessageSato(), satelliteMessageWithSatelliteNotExist());
    }
}
