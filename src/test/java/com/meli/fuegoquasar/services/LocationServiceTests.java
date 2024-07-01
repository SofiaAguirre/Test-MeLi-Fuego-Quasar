package com.meli.fuegoquasar.services;

import com.meli.fuegoquasar.exceptions.InvalidSatellitesException;
import com.meli.fuegoquasar.entities.Satellite;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LocationServiceTests {

    @Autowired
    private LocationService locationService;

    @Test(expected = InvalidSatellitesException.class)
    public void testInvalidSatellites() {
        // given
        Satellite satellite1 = new Satellite("kenovi", 100, new ArrayList<>());
        Satellite satellite2 = new Satellite("skywalker", 115.5, new ArrayList<>());
        Satellite satellite3 = new Satellite("sato", 142.7, new ArrayList<>());

        List<Satellite> satelliteList = Arrays.asList(satellite1, satellite2, satellite3);

        // when
        locationService.getSatellitePosition(satelliteList);
    }

    @Test
    public void testTrilaterationExact() {
        // given
        double[][] positions = new double[][]{{1.0, 1.0}, {1.0, 1.0}, {1.0, 1.0}};
        double[] distances = new double[]{1.0, 1.0, 1.0};
        double[] expectedPosition = new double[]{1.0, 1.0};

        // when
        double[] finalPosition = locationService.getLocation(positions, distances);

        // then
        assertArrayEquals(expectedPosition, finalPosition, 0.0);
    }

    @Test
    public void testTrilaterationInexact() {
        // given
        double[][] positions = new double[][]{{1.0, 1.0}, {3.0, 1.0}, {2.0, 2.0}};
        double[] distances = new double[]{0.9, 1.0, 1.0};
        double[] expectedPosition = new double[]{2.0, 1.0};

        // when
        double[] finalPosition = locationService.getLocation(positions, distances);

        // then
        assertNotEquals(Arrays.toString(expectedPosition), Arrays.toString(finalPosition));
    }

}
