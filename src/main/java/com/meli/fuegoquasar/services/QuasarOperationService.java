package com.meli.fuegoquasar.services;

import com.meli.fuegoquasar.config.CacheConfiguration;
import com.meli.fuegoquasar.exceptions.BadRequestException;
import com.meli.fuegoquasar.exceptions.InvalidSatellitesException;
import com.meli.fuegoquasar.entities.Position;
import com.meli.fuegoquasar.entities.Satellite;
import com.meli.fuegoquasar.entities.SatelliteName;
import com.meli.fuegoquasar.entities.dtos.TopSecretResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Service
public class QuasarOperationService {

    private final LocationService locationService;
    private final MessageDecoderService messageDecoderService;
    private final CacheConfiguration cacheConfiguration;

    private static final Integer QUANTITY_OF_SATELLITES = 3;

    @Autowired
    public QuasarOperationService(LocationService locationService, MessageDecoderService messageDecoderService, CacheConfiguration cacheConfiguration) {
        this.locationService = locationService;
        this.messageDecoderService = messageDecoderService;
        this.cacheConfiguration = cacheConfiguration;
    }

    public TopSecretResponse getTopSecretResponse(List<Satellite> satelliteList) {
        if (satelliteList.size() != QUANTITY_OF_SATELLITES) {
            throw new InvalidSatellitesException("ERROR: Missing information. Quantity of satellites (" + satelliteList.size() + ") is different than the required.");
        }

        Position position = locationService.getSatellitePosition(satelliteList);
        String message = messageDecoderService.getMessage(satelliteList);

        return new TopSecretResponse(position, message);
    }

    public void processSatelliteMessage(String satelliteName, double distance, List<String> messageList) {
        String name = SatelliteName.getByName(satelliteName).name();
        ConcurrentMap<String, Satellite> satellitesMap = cacheConfiguration.satelliteCache.asMap();

        if (satellitesMap.containsKey(name)) {
            throw new BadRequestException("ERROR: A message from Satellite " + name + " already exists in the loop.");
        }

        Satellite satellite = new Satellite(name, distance, messageList);
        cacheConfiguration.satelliteCache.put(name, satellite);
    }

    public TopSecretResponse getMessageFromSavedSatellites() {
        ConcurrentMap<String, Satellite> satellitesMap = cacheConfiguration.satelliteCache.asMap();
        List<Satellite> satelliteList = new ArrayList<>(satellitesMap.values());

        try {
            TopSecretResponse topSecretResponse = getTopSecretResponse(satelliteList);
            cacheConfiguration.satelliteCache.invalidateAll();
            return topSecretResponse;
        } catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
