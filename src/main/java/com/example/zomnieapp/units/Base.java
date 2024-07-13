package com.example.zomnieapp.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Base {
    private String id;
    private boolean isHead;
    private int range;
    private int x;
    private int y;
}