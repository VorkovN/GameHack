package com.example.zomnieapp.ui_app.data;

import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.ui.model.RenderMapPoint;
import com.example.zomnieapp.ui_app.data.model.VisibleMapPoint;

import java.util.List;

public interface RenderDataRepository {

    void listenToNewData(OnNewDataListener onNewDataListener);

    void onNewMap(List<VisibleMapPoint> points);

    void onNewPlayerStatus(PlayerStatus generatePlayerStatus);

    interface OnNewDataListener {

        void newMap(List<List<RenderMapPoint>> map);
        void newPlayer(PlayerStatus playerStatus);
    }
}