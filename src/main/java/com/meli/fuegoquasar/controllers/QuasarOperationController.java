package com.meli.fuegoquasar.controllers;

import com.meli.fuegoquasar.entities.dtos.TopSecretRequest;
import com.meli.fuegoquasar.entities.dtos.TopSecretResponse;
import com.meli.fuegoquasar.entities.dtos.MessageSplitReq;
import com.meli.fuegoquasar.entities.Satellite;
import com.meli.fuegoquasar.services.QuasarOperationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class QuasarOperationController {

    private final QuasarOperationService quasarOperationService;

    @Autowired
    public QuasarOperationController(QuasarOperationService quasarOperationService) {
        this.quasarOperationService = quasarOperationService;
    }

    @PostMapping("/topsecret")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Nivel 2: Servicio que recibe información de los Satelites y retorna el mensaje de auxilio")
    public TopSecretResponse postAllSatellitesMessages(@RequestBody TopSecretRequest topSecretRequest){
        List<Satellite> satelliteList = topSecretRequest.getSatellites();
        return quasarOperationService.getTopSecretResponse(satelliteList);
    }

    @PostMapping("/topsecret_split/{satelliteName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Nivel 3: Servicio que recibe información de un Satelite determinado")
    public void postSatelliteMessage(@PathVariable String satelliteName, @RequestBody MessageSplitReq messageSplitReq){
        quasarOperationService.processSatelliteMessage(satelliteName, messageSplitReq.getDistance(), messageSplitReq.getMessage());
    }

    @GetMapping("/topsecret_split")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Nivel 3: Servicio que retorna fuente y contenido del mensaje de auxilio")
    public TopSecretResponse getTopSecretMessage(){
        return quasarOperationService.getMessageFromSavedSatellites();
    }
}
