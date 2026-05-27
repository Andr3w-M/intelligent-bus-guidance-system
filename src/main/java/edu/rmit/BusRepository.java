package edu.rmit;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class BusRepository {

    /**
     * Map of bus key = Bus ID value = Bus Class
     */
    private final HashMap<String, Bus> busMap = new HashMap<>();

    /**
     * Add a Bus to the repository.
     *
     * @param bus the Bus to add (must have non-null ID)
     * @throws IllegalArgumentException if a bus with the same ID already exists
     */
    public void add(Bus bus) {
        if (busMap.containsKey(bus.getBusId())) {
            throw new IllegalArgumentException(
                    "Cannot add Bus: Bus ID already exist.");
        }
        this.busMap.put(bus.getBusId(), bus);
    }

    /**
     * Retrieve a Bus by its ID.
     *
     * @param id the bus ID
     * @return the Bus with the given ID
     * @throws NoSuchElementException if no Bus exists for the given ID
     */
    public Bus retrieve(String id) {
        Bus bus = this.busMap.get(id);
        if (bus == null) {
            throw new NoSuchElementException("Retrieval Failed: No record of bus exist.");
        }
        return bus;
    }

    /**
     * Update the driver assigned to the bus with the given ID.
     *
     * @param id the bus ID
     * @param d the new Driver
     */
    public void updateBusDriver(String id, Driver d) {
        Bus targetBus = retrieve(id);
        targetBus.setDriver(d);
    }

    /**
     * Update the seating capacity of the specified bus.
     *
     * @param id the bus ID
     * @param c new capacity
     */
    public void updateBusCapacity(String id, int c) {
        Bus targetBus = retrieve(id);
        targetBus.setCapacity(c);
    }

    /**
     * Update the fuel level for a bus.
     *
     * @param id the bus ID
     * @param lvl the new fuel level
     */
    public void updateBusFuelLevel(String id, double lvl) {
        Bus targetBus = retrieve(id);
        targetBus.setFuelLevel(lvl);
    }

    /**
     * Update the fuel type for a bus.
     *
     * @param id the bus ID
     * @param type the new fuel type string
     */
    public void updateBusFuelType(String id, String type) {
        Bus targetBus = retrieve(id);
        targetBus.setFuelType(type);
    }

    /**
     * Remove the bus with the given ID from the repository.
     *
     * @param id the bus ID to remove
     * @throws NoSuchElementException if no bus exists for the given ID
     */
    public void removeBus(String id) {
        Bus removedBus = this.busMap.remove(id);
        if (removedBus == null) {
            throw new NoSuchElementException("Retrieval Failed: No record of bus exist.");
        }
    }

    /**
     * @return number of buses in the repository
     */
    public int getBusCount() {
        return this.busMap.size();
    }
}
