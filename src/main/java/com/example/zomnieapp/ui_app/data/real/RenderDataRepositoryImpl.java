package com.example.zomnieapp.ui_app.data.real;

import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.RenderMapPoint;

import java.util.List;

public class RenderDataRepositoryImpl implements RenderDataRepository {

    private volatile OnNewDataListener onNewDataListener;

    @Override
    public void listenToNewData(OnNewDataListener onNewDataListener) {
        this.onNewDataListener = onNewDataListener;
    }

    @Override
    public void onNewMap(List<List<RenderMapPoint>> map) {
        tryEmitNewMap(map);
    }

    @Override
    public void onNewPlayerStatus(PlayerStatus playerStatus) {
        tryEmitNewPlayerStatus(playerStatus);
    }

    private void tryEmitNewMap(List<List<RenderMapPoint>> map) {
        try {
            onNewDataListener.newMap(map);
        } catch (Throwable t) {

        }
    }

    private void tryEmitNewPlayerStatus(PlayerStatus playerStatus) {
        try {
            onNewDataListener.newPlayer(playerStatus);
        } catch (Throwable t) {

        }
    }
}

