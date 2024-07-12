package com.example.zomnieapp.body;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BodyCommand {
    private List<Attack> attack;
    private List<Build> build;
    private MoveBase moveBase;
}
