package com.example.zomnieapp.ui_app.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderDataRepository {

    private volatile OnNewDataListener onNewDataListener;

    private volatile RenderData currentData = new RenderData(new ArrayList<String>(), new ArrayList<Color>());

    public void listenToNewData(OnNewDataListener onNewDataListener) {
        this.onNewDataListener = onNewDataListener;
    }

    public RenderData getCurrentData() {
        return currentData;
    }

    public void newTexts(List<String> texts) {
        RenderData newData = new RenderData(texts, currentData.getColors());
        currentData = newData;
        onNewDataListener.newData(newData);
    }
}
