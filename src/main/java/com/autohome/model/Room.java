package com.autohome.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {

    private int id;
    private String roomName;
    private List<Device> devices = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                roomName.equals(room.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomName);
    }
}
