package com.example.zomnieapp.ui_app.data.mock.model;

import com.example.zomnieapp.ui_app.data.model.MapPointType;
import com.example.zomnieapp.ui_app.data.model.MapSubject;

public class MockMapPoint {

    private MapPointType type;

    private MapSubject subject;

    public MockMapPoint(MapPointType type, MapSubject subject) {
        this.type = type;
        this.subject = subject;
    }

    public MapPointType getType() {
        return type;
    }

    public MapSubject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "RenderMapPoint{" +
                "type=" + type +
                ", subject=" + subject +
                '}';
    }
}