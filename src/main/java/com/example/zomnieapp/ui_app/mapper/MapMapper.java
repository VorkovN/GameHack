package com.example.zomnieapp.ui_app.mapper;

import com.example.zomnieapp.ui_app.data.model.RenderMapPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapMapper {

    public static List<List<Color>> mapToColors(List<List<RenderMapPoint>> map) {
        ArrayList<java.util.List<Color>> colorsInternal = new ArrayList<>();
        for (java.util.List<RenderMapPoint> renderMapPoints : map) {
            ArrayList<Color> colorsLine = new ArrayList<>();
            for (RenderMapPoint renderMapPoint : renderMapPoints) {
                colorsLine.add(renderMapPoint.getColor());
            }
            colorsInternal.add(colorsLine);
        }
        return colorsInternal;
    }
}
