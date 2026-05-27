package edu.rmit;

import java.time.LocalDate;
import java.time.Period;

public class Bus {

    private enum FuelType {
        DIESEL,
        HYBRID,
        ELECTRICITY;

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

    private int getAge(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();
        Period age = Period.between(parsedDate, now);
        return age.getYears();
    }

    private boolean validateDriver(Driver d) {
        if (getAge(d.getBirthdate()) > 50 && this.capacity >= 50) {
            throw new IllegalArgumentException(
                    "Driver Age Restriction: Driver age over 50 can't operate buses with a capacity more than or equal to 50.");
        } else if (d.getYearsOfExperience() < 5 && this.fuelType == FuelType.ELECTRICITY) {
            throw new IllegalArgumentException(
                    "Electric Bus Restriction: Only drivers with at least 5 years of experience can drive electric buses.");
        } else if ((d.getLicenceType().equals("Light")
                || (d.getLicenceType().equals("Medium")) && !(this.fuelType == FuelType.DIESEL))) {
            throw new IllegalArgumentException(
                    "Driver Licence Restriction: Only drivers holding a Heavy or Public Transport licence are permitted to operate electric and hybrid buses.");
        }
        return true;
    }

    private boolean validateID(String id) {
        if (id == null || id.length() != 8) {
            throw new IllegalArgumentException("Invalid Bus ID Format: must be exactly 8 digits.");
        } else if (!id.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid Bus ID Format: Only digits (0-9) are allowed.");
        }
        return true;
    }

    public String getBusId() {
        return this.busID;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver d) {
        if (validateDriver(d)) {
            this.driver = d;
        }
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int c) {
        this.capacity = c;
    }

    public double getFuelLevel() {
        return this.fuelLevel;
    }

    public void setFuelLevel(double lvl) {
        this.fuelLevel = lvl;
    }

    public String getFuelType() {
        return this.fuelType.toString();
    }

    public void setFuelType(String type) {
        this.fuelType = FuelType.valueOf(type.toUpperCase());
    }

}
