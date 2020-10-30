package com.mercadolibre.firequasar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class MessageReceived {

    private int id;
    private Satellite satellite;
    private DistanceMessage distanceMessage;

}
