package com.meli.fuegoquasar.services;

import com.meli.fuegoquasar.config.CacheConfiguration;
import com.meli.fuegoquasar.entities.Position;
import com.meli.fuegoquasar.entities.Satellite;
import com.meli.fuegoquasar.entities.dtos.TopSecretResponse;
import com.meli.fuegoquasar.exceptions.BadRequestException;
import com.meli.fuegoquasar.exceptions.InvalidSatellitesException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuasarOperationServiceTests {

    @Mock
    private LocationService locationService;

    @Mock
    private MessageDecoderService messageDecoderService;

    @Spy
    private CacheConfiguration cacheConfiguration;

    @InjectMocks
    private QuasarOperationService quasarOperationService;

    @Test(expected = InvalidSatellitesException.class)
    public void testSatellitesSizeNotEqualTo3(){
        // given
        Satellite satellite1 = new Satellite("kenobi", 100, Arrays.asList("este", "", "", "mensaje", ""));
        Satellite satellite2 = new Satellite("skywalker", 115.5, Arrays.asList("", "es", "", "",  "secreto"));

        // when
        List<Satellite> satelliteList = Arrays.asList(satellite1, satellite2);
        quasarOperationService.getTopSecretResponse(satelliteList);
    }

    @Test
    public void testGetTopSecretResponseValidSatellites() {
        // given
        List<Satellite> satelliteList = new ArrayList<>();
        satelliteList.add(new Satellite("kenobi", 100, Arrays.asList("este", "", "", "mensaje", "")));
        satelliteList.add(new Satellite("skywalker", 115.5, Arrays.asList("", "es", "", "",  "secreto")));
        satelliteList.add(new Satellite("sato", 142.7, Arrays.asList("este", "", "un", "", "")));

        Position expectedPosition = new Position(0.0, 0.0);
        String expectedMessage = "este es un mensaje secreto";

        when(locationService.getSatellitePosition(satelliteList)).thenReturn(expectedPosition);
        when(messageDecoderService.getMessage(satelliteList)).thenReturn(expectedMessage);

        // when
        TopSecretResponse response = quasarOperationService.getTopSecretResponse(satelliteList);

        // then
        assertEquals(expectedPosition, response.getPosition());
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    public void testProcessSatelliteMessageNewSatellite() {
        // given
        String satelliteName = "KENOBI";
        double distance = 100;
        List<String> messageList = Arrays.asList("este", "", "", "mensaje", "");

        // when
        quasarOperationService.processSatelliteMessage(satelliteName, distance, messageList);

        // then
        assertTrue(cacheConfiguration.satelliteCache.asMap().containsKey(satelliteName));
    }

    @Test(expected = BadRequestException.class)
    public void testProcessSatelliteMessageSatelliteAlreadyExists() {
        // given
        String satelliteName = "KENOBI";
        double distance = 100;
        List<String> messageList = Arrays.asList("este", "", "", "mensaje", "");
        Satellite satellite = new Satellite(satelliteName, distance, messageList);
        cacheConfiguration.satelliteCache.put(satelliteName, satellite);

        // when
        quasarOperationService.processSatelliteMessage(satelliteName, distance, messageList);

        // then
        assertTrue(cacheConfiguration.satelliteCache.asMap().containsKey(satelliteName));
    }
}
