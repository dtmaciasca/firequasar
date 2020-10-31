package com.mercadolibre.firequasar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DataControl implements Serializable {

    private Date fechaInsert;
    private Date fechaUpdate;
    private Date fechaDelete;
    private boolean deleted;
    private Integer version;

}
