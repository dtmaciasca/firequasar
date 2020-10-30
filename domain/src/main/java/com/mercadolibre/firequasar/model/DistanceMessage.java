package com.mercadolibre.firequasar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@SuperBuilder
public class DistanceMessage {

    @NotNull
    private double distance;
    @NotNull
    private String [] message;

}
