package com.autohome.service;

import com.autohome.dao.DeviceRepo;
import com.autohome.dao.RoomRepo;
import com.autohome.model.Device;
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

}
