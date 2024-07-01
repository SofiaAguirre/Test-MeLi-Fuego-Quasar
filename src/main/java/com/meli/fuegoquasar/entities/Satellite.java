package com.meli.fuegoquasar.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Satellite {
    private String name;
    private double distance;
    private List<String> message;
}
