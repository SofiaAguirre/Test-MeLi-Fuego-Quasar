package com.meli.fuegoquasar.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.meli.fuegoquasar.entities.Satellite;
import org.springframework.stereotype.Component;

@Component
public class CacheConfiguration {

    public Cache<String, Satellite> satelliteCache = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(3)
            .build();
}
