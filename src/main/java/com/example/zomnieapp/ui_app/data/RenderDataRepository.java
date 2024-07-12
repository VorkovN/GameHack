package com.example.zomnieapp.ui_app.data;

import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.RenderMapPoint;

import java.util.List;

public interface RenderDataRepository {

    void listenToNewData(OnNewDataListener onNewDataListener);

    void onNewMap(List<List<RenderMapPoint>> map);

    void onNewPlayerStatus(PlayerStatus generatePlayerStatus);

    interface OnNewDataListener {

        void newMap(List<List<RenderMapPoint>> map);
        void newPlayer(PlayerStatus playerStatus);
    }
}