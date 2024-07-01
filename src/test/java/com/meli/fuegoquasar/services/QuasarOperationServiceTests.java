package com.meli.fuegoquasar.services;

import com.meli.fuegoquasar.config.CacheConfiguration;
import com.meli.fuegoquasar.entities.Satellite;
import com.meli.fuegoquasar.exceptions.InvalidSatellitesException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuasarOperationServiceTests {

    @Mock
    private LocationService locationService;

    @Mock
    private MessageDecoderService messageDecoderService;

    @Mock
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
}
