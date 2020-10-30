package com.mercadolibre.firequasar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Satellite {

    private Integer id;
    private String name;
    private Position position;

}
