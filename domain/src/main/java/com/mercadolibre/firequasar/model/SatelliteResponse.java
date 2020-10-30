package com.mercadolibre.firequasar.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
public class SatelliteResponse {

    @NotNull
    private Position position;
    @NotNull
    private String message;

}
