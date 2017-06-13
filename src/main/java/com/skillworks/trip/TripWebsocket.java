package com.skillworks.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
class TripWebsocket {

    private static Random RANDOM = new Random();

    @Autowired
    private SimpMessagingTemplate websocket;

    @Scheduled(fixedRate = 2000)
    private void start() {
        System.out.println("running trip scheduler....");
        int n = RANDOM.nextInt(10) + 1;
        Set<Trip> trips = new HashSet<>();
        for(int i=0; i < n + 500; i++) {
            trips.add(createRandomTrip());
        }
        publish(trips);
    }

    private Trip createRandomTrip() {
        Trip t1 = new Trip();
        t1.setTripId(1008 + + RANDOM.nextInt());
        t1.setTripDescription(t1.getTripId() + " trip desc");
        return t1;
    }

    private void publish(Set<Trip> trips) {
        System.out.println("publishing trip size...." + trips.size());
        websocket.convertAndSend("/channel/trip", trips);
    }
}
