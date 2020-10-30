package com.mercadolibre.firequasar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder
public class SatelliteRequest {

    @NotNull
    private List<SatelliteMessage> satellites;

}
