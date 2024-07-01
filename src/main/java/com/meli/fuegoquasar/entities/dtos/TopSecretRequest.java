package com.meli.fuegoquasar.entities.dtos;

import com.meli.fuegoquasar.entities.Satellite;
import lombok.Getter;

import java.util.List;

@Getter
public class TopSecretRequest {
    private List<Satellite> satellites;
}
