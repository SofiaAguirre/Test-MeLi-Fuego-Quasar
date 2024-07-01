package com.meli.fuegoquasar.entities.dtos;

import lombok.Getter;

import java.util.List;

@Getter
public class MessageSplitReq {
    private double distance;
    private List<String> message;
}
