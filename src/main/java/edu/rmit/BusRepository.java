package edu.rmit;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class BusRepository {

    private final HashMap<String, Bus> busMap = new HashMap<>();

    public void add(Bus bus) {
        if (busMap.containsKey(bus.getBusId())) {
            throw new IllegalArgumentException(
                    "Cannot add Bus: Bus ID already exist.");
        }
        this.busMap.put(bus.getBusId(), bus);
    }

    public Bus retrieve(String id) {
        Bus bus = this.busMap.get(id);
        if (bus == null) {
            throw new NoSuchElementException("Retrieval Failed: No record of bus exist.");
        }
        return bus;
    }

    public void updateBusDriver(String id, Driver d) {
        Bus targetBus = retrieve(id);
        targetBus.setDriver(d);
    }

    public void updateBusCapacity(String id, int c) {
        Bus targetBus = retrieve(id);
        targetBus.setCapacity(c);
    }

    public void updateBusFuelLevel(String id, double lvl) {
        Bus targetBus = retrieve(id);
        targetBus.setFuelLevel(lvl);
    }

    public void updateBusFuelType(String id, String type) {
        Bus targetBus = retrieve(id);
        targetBus.setFuelType(type);
    }

    public void removeBus(String id) {
        Bus removedBus = this.busMap.remove(id);
        if (removedBus == null) {
            throw new NoSuchElementException("Retrieval Failed: No record of bus exist.");
        }
    }

    public int getBusCount() {
        return this.busMap.size();
    }
}
