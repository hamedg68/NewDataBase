package com.example.hamed.newdatabase;

/**
 * Created by hamed on 7/6/18.
 */

public class DeviceModel {
    private int ID;
    private String deviceName;
    private int status;
    private int state;
    private long lastUpdate;
    private String simCard;
    private int needResponse;


    public DeviceModel() {
    }

    public DeviceModel(int ID, String deviceName, int status, int state, int lastUpdate, String simCard, int needResponse) {
        this.ID = ID;
        this.deviceName = deviceName;
        this.status = status;
        this.state = state;
        this.lastUpdate = lastUpdate;
        this.simCard = simCard;
        this.needResponse = needResponse;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public int getNeedResponse() {
        return needResponse;
    }

    public void setNeedResponse(int needResponse) {
        this.needResponse = needResponse;
    }
}
