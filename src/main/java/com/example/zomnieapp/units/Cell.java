package com.example.zomnieapp.units;

import com.example.zomnieapp.ui_app.data.model.MapSubject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Cell {
    private String type; // "zombie", "wall", "default", "base", "enemyBlock", "free"
    private int x;
    private int y;

    private MapSubject mapSubject;
}
