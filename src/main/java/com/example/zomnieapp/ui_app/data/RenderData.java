package com.example.zomnieapp.ui_app.data;

import java.awt.*;
import java.util.List;

public class RenderData {
    private List<String> texts;
    private List<Color> colors;

    public RenderData(List<String> texts, List<Color> colors) {
        this.texts = texts;
        this.colors = colors;
    }

    public List<String> getTexts() {
        return texts;
    }

    public List<Color> getColors() {
        return colors;
    }
}