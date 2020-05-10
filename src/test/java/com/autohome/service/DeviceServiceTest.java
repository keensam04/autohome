package com.autohome.service;

import com.autohome.dao.DeviceRepo;
import com.autohome.dao.RoomRepo;
import com.autohome.model.Device;
import com.autohome.model.Room;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DeviceServiceTest {

    @Mock
    private DeviceRepo deviceRepo;

    @Mock
    private RoomRepo roomRepo;

    @InjectMocks
    private DeviceService deviceService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddDevice() {
        int roomId = 1;
        Device device = new Device();
        device.setId(1);
        device.setDeviceName("testDevice");

        Mockito.when(deviceRepo.getDeviceByName(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
        Mockito.when(deviceRepo.addDevice(Mockito.anyInt(), Mockito.any(Device.class))).thenReturn(1);
        Mockito.when(roomRepo.getRoom(Mockito.anyInt())).thenReturn(new Room());

        int rowsEffected = deviceService.addDevice(roomId, device);

        Assert.assertEquals(1, rowsEffected);
    }

    @Test
    public void testAddDeviceWhenDeviceNull() {
        int roomId = 1;

        Mockito.when(deviceRepo.getDeviceByName(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
        Mockito.when(deviceRepo.addDevice(Mockito.anyInt(), Mockito.any(Device.class))).thenReturn(1);

        int rowsEffected = deviceService.addDevice(roomId, null);

        Assert.assertEquals(-1, rowsEffected);
    }

    @Test
    public void testAddDeviceWhenInvalidRoomId() {
        int roomId = 8;
        Device device = new Device();
        device.setId(1);
        device.setDeviceName("testDevice");

        Mockito.when(deviceRepo.addDevice(Mockito.anyInt(), Mockito.any(Device.class))).thenReturn(0);
        Mockito.when(deviceRepo.getDeviceByName(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
        Mockito.when(roomRepo.getRoom(Mockito.anyInt())).thenReturn(null);

        int rowsEffected = deviceService.addDevice(roomId, device);
        Mockito.verifyZeroInteractions(deviceRepo);
        Assert.assertEquals(-1, rowsEffected);

    }

    @Test
    public void testAddDeviceWithDuplicateDeviceNameInSameRoom() {
        int roomId = 1;
        Device device = new Device();
        device.setId(1);
        device.setDeviceName("duplicate");

        Device deviceFromDb = new Device();
        deviceFromDb.setRoomId(1);
        deviceFromDb.setDeviceName("duplicate");

        Mockito.when(deviceRepo.addDevice(Mockito.anyInt(), Mockito.any(Device.class))).thenReturn(0);
        Mockito.when(deviceRepo.getDeviceByName(Mockito.anyInt(), Mockito.anyString())).thenReturn(deviceFromDb);
        Mockito.when(roomRepo.getRoom(Mockito.anyInt())).thenReturn(new Room());

        int rowsEffected = deviceService.addDevice(roomId, device);
        Mockito.verify(deviceRepo, Mockito.never()).addDevice(Mockito.anyInt(), Mockito.any(Device.class));

        Assert.assertEquals(-1, rowsEffected);

    }

    @Test
    public void testAddDeviceWithDuplicateDeviceNameInDifferentRoom() {
        int roomId = 1;
        Device device = new Device();
        device.setId(1);
        device.setDeviceName("test");

        Device deviceFromDb = new Device();
        deviceFromDb.setRoomId(2);
        deviceFromDb.setDeviceName("duplicate");

        Mockito.when(deviceRepo.getDeviceByName(Mockito.anyInt(), Mockito.anyString())).thenReturn(deviceFromDb);
        Mockito.when(deviceRepo.addDevice(Mockito.anyInt(), Mockito.any(Device.class))).thenReturn(1);
        Mockito.when(roomRepo.getRoom(Mockito.anyInt())).thenReturn(new Room());

        int rowsEffected = deviceService.addDevice(roomId, device);
        Assert.assertEquals(1, rowsEffected);
    }

    @Test
    public void testAddDeviceWithZeroRoomId() {
        int roomId = 0;
        Device device = new Device();
        device.setId(1);
        device.setDeviceName("test");

        Mockito.when(deviceRepo.getDeviceByName(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
        Mockito.when(deviceRepo.addDevice(Mockito.anyInt(), Mockito.any(Device.class))).thenReturn(0);
        Mockito.when(roomRepo.getRoom(Mockito.anyInt())).thenReturn(null);

        int rowsEffected = deviceService.addDevice(roomId, device);

        Mockito.verifyZeroInteractions(deviceRepo);
        Assert.assertEquals(-1, rowsEffected);
    }
}
