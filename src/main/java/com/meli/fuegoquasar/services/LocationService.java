package com.meli.fuegoquasar.services;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.meli.fuegoquasar.entities.SatelliteName;
import com.meli.fuegoquasar.exceptions.InvalidSatellitesException;
import com.meli.fuegoquasar.entities.Position;
import com.meli.fuegoquasar.entities.Satellite;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Value("${kenobi.coordinates}")
    private double[] KENOVI_COORDINATES;

    @Value("${skywalker.coordinates}")
    private double[] SKYWALKER_COORDINATES;

    @Value("${sato.coordinates}")
    private double[] SATO_COORDINATES;

    public double[] getLocation(double[][] positions, double[] distances) {
        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
            new TrilaterationFunction(positions, distances),
            new LevenbergMarquardtOptimizer()
        );

        return  solver.solve().getPoint().toArray();
    }

    public Position getSatellitePosition(List<Satellite> satelliteList) {
        Map<String, Satellite> satelliteMap = satelliteList.stream()
                .collect(Collectors.toMap(satellite -> satellite.getName().toLowerCase(), Function.identity()));

        Satellite kenobi = getSatelliteByName(satelliteMap, SatelliteName.KENOBI.toString());
        Satellite skywalker = getSatelliteByName(satelliteMap, SatelliteName.SKYWALKER.toString());
        Satellite sato = getSatelliteByName(satelliteMap, SatelliteName.SATO.toString());

        double[][] positions = new double[][] { KENOVI_COORDINATES, SKYWALKER_COORDINATES, SATO_COORDINATES } ;
        double[] distances = new double[]{ kenobi.getDistance(), skywalker.getDistance(), sato.getDistance() };
        double[] positionArr = getLocation(positions, distances);

        return new Position(positionArr[0], positionArr[1]);
    }

    private Satellite getSatelliteByName(Map<String, Satellite> satelliteMap, String name) {
        Satellite satellite = satelliteMap.get(name);
        if (satellite == null) {
            throw new InvalidSatellitesException("ERROR: Satellite " + name + " is not present on request");
        }
        return satellite;
    }
}
