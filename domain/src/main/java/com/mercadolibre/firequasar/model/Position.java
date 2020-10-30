package com.mercadolibre.firequasar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class Position {

    private double x;
    private double y;

    @JsonIgnore
    public double[] getPosition(){
        return new double[]{x,y};
    }
}
