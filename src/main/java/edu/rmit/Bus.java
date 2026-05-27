package edu.rmit;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Bus {

    private enum FuelType {
        DIESEL,
        HYBRID,
        ELECTRICITY;

        /*
         * Format ENUM to title style Strings for getFuelType
         */
        @Override
        public String toString() {
            String name = this.name();
            if (name.isEmpty()) {
                return name;
            }
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
    }

    private String busID;
    private Driver driver;
    private int capacity;
    private double fuelLevel;
    private FuelType fuelType;

    /**
     * Create a new Bus instance after validating inputs.
     *
     * @param id the bus ID
     * @param d the assigned Driver
     * @param c capacity
     * @param lvl initial fuel level
     * @param type fuel type string (e.g. "Diesel", "Hybrid", "Electricity")
     */
    public Bus(String id, Driver d, int c, double lvl, String type) {
        if (validateID(id)) {
            this.busID = id;
        }
        this.capacity = c;
        this.fuelLevel = lvl;
        this.fuelType = FuelType.valueOf(type.toUpperCase());
        if (validateDriver(d)) {
            this.driver = d;
        }
    }

    /**
     * Compute age in years from a birthdate string in dd-MM-yyyy format.
     *
     * @param date birthdate string in format dd-MM-yyyy
     * @return age in years
     */
    private int getAge(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();
        Period age = Period.between(parsedDate, now);
        return age.getYears();
    }

    /**
     * Validate B3 - B5 for a driver assigned to this bus. Throws
     * IllegalArgumentException when a rule is violated.
     *
     * @param d the Driver to validate
     * @return true when validation passes
     */
    private boolean validateDriver(Driver d) {
        // B3: Driver Age Restriction 
        if (getAge(d.getBirthdate()) > 50 && this.capacity >= 50) {
            throw new IllegalArgumentException(
                    "Driver Age Restriction: Driver age over 50 can't operate buses with a capacity more than or equal to 50.");
        }

        // B4: Electric Bus Restriction 
        if (d.getYearsOfExperience() < 5 && this.fuelType == FuelType.ELECTRICITY) {
            throw new IllegalArgumentException(
                    "Electric Bus Restriction: Only drivers with at least 5 years of experience can drive electric buses.");
        }

        // B5: Driver Licence Restriction 
        if ((d.getLicenceType().equals("Light")
                || d.getLicenceType().equals("Medium")) && !(this.fuelType == FuelType.DIESEL)) {
            throw new IllegalArgumentException(
                    "Driver Licence Restriction: Only drivers holding a Heavy or Public Transport licence are permitted to operate electric and hybrid buses.");
        }
        return true;
    }

    /**
     * Validate bus ID (B1).
     *
     * @param id the ID string to validate
     * @return true when id is valid, otherwise throws IllegalArgumentException
     */
    private boolean validateID(String id) {
        if (id == null || id.length() != 8) {
            throw new IllegalArgumentException("Invalid Bus ID Format: must be exactly 8 digits.");
        } else if (!id.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid Bus ID Format: Only digits (0-9) are allowed.");
        }
        return true;
    }

    /**
     * @return this bus's ID
     */
    public String getBusId() {
        return this.busID;
    }

    /**
     * @return the assigned Driver (may be null)
     */
    public Driver getDriver() {
        return this.driver;
    }

    /**
     * Set a new driver after validating driver rules.
     *
     * @param d the Driver to assign
     */
    public void setDriver(Driver d) {
        if (validateDriver(d)) {
            this.driver = d;
        }
    }

    /**
     * @return the seating capacity of the bus
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Set the bus seating capacity.
     *
     * @param c new capacity
     */
    public void setCapacity(int c) {
        this.capacity = c;
    }

    /**
     * @return the current fuel level
     */
    public double getFuelLevel() {
        return this.fuelLevel;
    }

    /**
     * Update the fuel level.
     *
     * @param lvl new fuel level
     */
    public void setFuelLevel(double lvl) {
        this.fuelLevel = lvl;
    }

    /**
     * @return the fuel type as a human-friendly string
     */
    public String getFuelType() {
        return this.fuelType.toString();
    }

    /**
     * Set the fuel type from a string (case-insensitive).
     *
     * @param type fuel type name
     */
    public void setFuelType(String type) {
        this.fuelType = FuelType.valueOf(type.toUpperCase());
    }

}
