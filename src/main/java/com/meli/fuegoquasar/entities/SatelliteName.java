package com.meli.fuegoquasar.entities;

import com.meli.fuegoquasar.exceptions.UnknownSatelliteException;

public enum SatelliteName {
    KENOBI,
    SKYWALKER,
    SATO;

    public static SatelliteName getByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownSatelliteException("Unknown SatelliteName: " + name);
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
