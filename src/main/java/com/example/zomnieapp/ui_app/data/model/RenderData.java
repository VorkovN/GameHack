package com.example.zomnieapp.ui_app.data.model;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderData {
    private volatile List<String> texts;
    private volatile List<List<Color>> colors;

    private volatile List<List<RenderMapPoint>> map = new ArrayList<>();

    private volatile PlayerStatus playerStatus = new PlayerStatus(0, LocalDateTime.MIN, 10, "test", 0, 0);

    public RenderData(List<String> texts, List<List<Color>> colors) {
        this.texts = texts;
        this.colors = colors;
    }

    public RenderData(List<List<RenderMapPoint>> map, PlayerStatus playerStatus) {
        this.texts = Collections.singletonList(playerStatus.toString());
        ArrayList<List<Color>> colorsInternal = new ArrayList<>();
        for (List<RenderMapPoint> renderMapPoints : map) {
            ArrayList<Color> colorsLine = new ArrayList<>();
            for (RenderMapPoint renderMapPoint : renderMapPoints) {
                colorsLine.add(renderMapPoint.getColor());
            }
            colorsInternal.add(colorsLine);
        }
        this.colors = colorsInternal;
    }

    public List<String> getTexts() {
        return texts;
    }

    public List<List<Color>> getColors() {
        return colors;
    }

    public List<List<RenderMapPoint>> getMap() {
        return map;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
}