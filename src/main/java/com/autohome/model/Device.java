package com.autohome.model;

import java.time.Instant;
import java.util.Date;

public class Device {

    private int id;
    private String deviceName;
    private Date dateOfPurchase;
    private Date dateOfExpiry;
    private boolean isSwitch;
    private float powerRating;
    private int roomId;
    private String onboardedBy;
    private Instant dateOfOnboarding;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Date getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public boolean getIsSwitch() {
        return isSwitch;
    }

    public void setIsSwitch(boolean aSwitch) {
        isSwitch = aSwitch;
    }

    public float getPowerRating() {
        return powerRating;
    }

    public void setPowerRating(float powerRating) {
        this.powerRating = powerRating;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getOnboardedBy() {
        return onboardedBy;
    }

    public void setOnboardedBy(String onboardedBy) {
        this.onboardedBy = onboardedBy;
    }

    public Instant getDateOfOnboarding() {
        return dateOfOnboarding;
    }

    public void setDateOfOnboarding(Instant dateOfOnboarding) {
        this.dateOfOnboarding = dateOfOnboarding;
    }

}
