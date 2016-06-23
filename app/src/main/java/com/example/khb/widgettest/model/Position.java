package com.example.khb.widgettest.model;

/**
 * Created by khb on 2016/6/21.
 */
public class Position {
    String id;
    String saleId;
    String saleName;
    double lon;
    double lat;
    int isShow;
    long propertyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id='" + id + '\'' +
                ", saleId='" + saleId + '\'' +
                ", saleName='" + saleName + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", isShow=" + isShow +
                ", propertyId=" + propertyId +
                '}';
    }
}
