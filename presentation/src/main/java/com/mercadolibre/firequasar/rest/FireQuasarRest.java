package com.mercadolibre.firequasar.rest;

import com.mercadolibre.firequasar.model.DistanceMessage;
import com.mercadolibre.firequasar.model.SatelliteRequest;
import com.mercadolibre.firequasar.service.FireQuasarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/firequasar")
@Validated
public class FireQuasarRest {

    @Autowired
    private FireQuasarService fireQuasarService;

    @PostMapping(value = "/topsecret/")
    @Operation(summary = "Obtiene la localización y mensaje con mínimo 2 y máximo 3 satélites")
    public ResponseEntity<Object> getUbicationShapeAndMessage(@RequestBody @Valid SatelliteRequest satelliteRequest) {
        try {
            return new ResponseEntity<>(fireQuasarService.getUbicationShapeAndMessage(satelliteRequest), HttpStatus.OK) ;
        }catch (FireQuasarService.FireQuasarServiceException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/topsecret_split/{satellite_name}", produces = "text/plain;charset=UTF-8")
    @Operation(summary = "Guardar la distancia y mensaje del satélite")
    public ResponseEntity<String> saveDistanceAndMessageSatellite(@PathVariable(name = "satellite_name") String satelliteName, @RequestBody @Valid DistanceMessage distanceMessage) {
        try{
            return new ResponseEntity<>(fireQuasarService.saveDistanceAndMessageSatellite(satelliteName, distanceMessage), HttpStatus.OK);
        }catch (FireQuasarService.FireQuasarServiceException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/topsecret_split/")
    @Operation(summary = "Obtener el último mensaje que se recibió por cada satélite")
    public ResponseEntity<Object> getUbicationShapeAndMessage() {
        try {
            return new ResponseEntity<>(fireQuasarService.getPositionAndMessage(), HttpStatus.OK);
        }catch (FireQuasarService.NotInformationEnoughException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
