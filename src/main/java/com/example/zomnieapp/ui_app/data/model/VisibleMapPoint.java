package com.example.zomnieapp.ui_app.data.model;

public class VisibleMapPoint {
    private final MapPointType type;

    private final MapSubject subject;

    private final Coordinate coordinate;

    public VisibleMapPoint(MapPointType type, MapSubject subject, Coordinate coordinate) {
        this.type = type;
        this.subject = subject;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "RenderMapPoint{" +
                "type=" + type +
                ", subject=" + subject +
                '}';
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public MapSubject getSubject() {
        return subject;
    }

    public MapPointType getType() {
        return type;
    }
}
