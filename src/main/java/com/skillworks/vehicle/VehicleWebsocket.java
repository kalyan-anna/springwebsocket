package com.skillworks.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
class VehicleWebsocket {

    private static Random RANDOM = new Random();

    @Autowired
    private SimpMessagingTemplate websocket;

    @Scheduled(fixedRate = 2000)
    private void start() {
        System.out.println("running scheduler....");
        int n = RANDOM.nextInt(10) + 1;
        Set<Vehicle> vehicles = new HashSet<>();
        for(int i=0; i < n ; i++) {
            vehicles.add(createRandomVehicle());
        }
        publish(vehicles);
    }

    private Vehicle createRandomVehicle() {
        Vehicle v1 = new Vehicle();
        v1.setVehicleId(9994 + RANDOM.nextInt());
        v1.setServiceDescription( v1.getVehicleId() + " service description");
        v1.setRouteId(RANDOM.nextInt());
        return v1;
    }

    private void publish(Set<Vehicle> vehicles) {
        System.out.println("publishing vehicle size...." + vehicles.size());
        websocket.convertAndSend("/channel/vehicle", vehicles);
    }
}
