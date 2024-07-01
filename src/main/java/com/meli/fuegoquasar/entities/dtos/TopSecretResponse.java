package com.meli.fuegoquasar.entities.dtos;

import com.meli.fuegoquasar.entities.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretResponse {
    private Position position;
    private String message;
}
