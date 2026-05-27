package edu.rmit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class BusTest {

    /**
     * Test B1 on valid input
     */
    @Test
    public void test_busId_validInput_Success() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        Bus bus = new Bus("12345678", driver_1, 40, 1.0, "DIESEL");

        String expected = "12345678";
        String actual = bus.getBusId();

        assertEquals(expected, actual);
    }

    /**
     * Test B1 on white space string
     */
    @Test
    public void test_busId_whitespaced_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Bus bus = new Bus("123 5678", driver_1, 40, 1.0, "DIESEL");
        });

        String expected = "Invalid Bus ID Format: Only digits (0-9) are allowed.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B1 on empty string
     */
    @Test
    public void test_busId_emptyStrings_Success() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Bus bus = new Bus("", driver_1, 40, 1.0, "DIESEL");
        });

        String expected = "Invalid Bus ID Format: must be exactly 8 digits.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B2 on removing a bus
     */
    @Test
    public void test_busCount_validInput_Success() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        BusRepository busRepo = new BusRepository();
        Bus bus_1 = new Bus("12345678", driver_1, 40, 1.0, "DIESEL");
        Bus bus_2 = new Bus("24681012", driver_1, 40, 1.0, "DIESEL");
        Bus bus_3 = new Bus("36912151", driver_1, 40, 1.0, "DIESEL");
        busRepo.add(bus_1);
        busRepo.add(bus_2);
        busRepo.add(bus_3);

        int beforeBusCount = busRepo.getBusCount();
        busRepo.removeBus("24681012");
        int afterBusCount = busRepo.getBusCount();

        int expectedBefore = 3;
        int expectedAfter = 2;
        assertEquals(expectedBefore, beforeBusCount);
        assertEquals(afterBusCount, expectedAfter);
    }

    /**
     * Test B2 on updating bus on invalid driver
     */
    @Test
    public void test_busCount_invalidDriver_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        Driver driver_2 = new Driver("08-08-2008", 4, "Heavy");
        BusRepository busRepo = new BusRepository();
        Bus bus_1 = new Bus("12345678", driver_1, 40, 1.0, "DIESEL");
        Bus bus_3 = new Bus("36912151", driver_1, 40, 1.0, "Electricity");
        busRepo.add(bus_1);
        busRepo.add(bus_3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            busRepo.updateBusDriver("36912151", driver_2);
        });

        String expected = "Electric Bus Restriction: Only drivers with at least 5 years of experience can drive electric buses.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B2 on updating capacity bus on an incorrect busId
     */
    @Test
    public void test_busCount_incorrectBusId_NoSuchElementException() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        BusRepository busRepo = new BusRepository();
        Bus bus_1 = new Bus("12345678", driver_1, 40, 1.0, "DIESEL");
        Bus bus_3 = new Bus("36912151", driver_1, 40, 1.0, "Electricity");
        busRepo.add(bus_1);
        busRepo.add(bus_3);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            busRepo.updateBusCapacity("12335678", 123);
        });

        String expected = "Retrieval Failed: No record of bus exist.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B3 on age exactly 50 driver age and bus capacity
     */
    @Test
    public void test_bus_validInput_Success() {
        Driver driver_1 = new Driver("02-04-1976", 6, "Heavy");

        Bus bus = new Bus("12345678", driver_1, 50, 1.0, "DIESEL");

        int expected = 50;
        int actual = bus.getCapacity();
        assertEquals(driver_1, bus.getDriver());
        assertEquals(expected, actual);
    }

    /**
     * Test B3 on driver age above 50 and below 50 capacity
     */
    @Test
    public void test_bus_oneTrueInput_Success() {
        Driver driver_1 = new Driver("07-11-1911", 6, "Heavy");

        Bus bus = new Bus("12345678", driver_1, 49, 1.0, "DIESEL");

        int expected = 49;
        int actual = bus.getCapacity();
        assertEquals(driver_1, bus.getDriver());
        assertEquals(expected, actual);
    }

    /**
     * Test B3 on driver age exactly 51 and 60 bus capacity
     */
    @Test
    public void test_bus_outOfBoundsInput_IllegalArgumentException() {
        String todayWithFixedYear = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-")) + "1975";
        Driver driver_1 = new Driver(todayWithFixedYear, 6, "Heavy");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Bus bus = new Bus("12345678", driver_1, 60, 1.0, "DIESEL");
        });

        String expected = "Driver Age Restriction: Driver age over 50 can't operate buses with a capacity more than or equal to 50.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B4 on driver 5 years experience, on electric bus
     */
    @Test
    public void test_bus_validDrivingExperience_Success() {
        Driver driver_1 = new Driver("08-08-2008", 5, "Heavy");
        Bus bus = new Bus("12345678", driver_1, 60, 1.0, "Electricity");

        String expected = "Electricity";
        String actual = bus.getFuelType();
        assertEquals(driver_1, bus.getDriver());
        assertEquals(expected, actual);
    }

    /**
     * Test B4 on driver 4 years experience, on electric bus
     */
    @Test
    public void test_bus_underBoundForElectricity_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 4, "Heavy");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Bus bus = new Bus("12345678", driver_1, 60, 1.0, "Electricity");
        });

        String expected = "Electric Bus Restriction: Only drivers with at least 5 years of experience can drive electric buses.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B4 on driver 0 years experience, on hybrid bus
     */
    @Test
    public void test_bus_noExperienceOnOtherFuelType_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 0, "Heavy");
        Bus bus = new Bus("12345678", driver_1, 60, 1.0, "Hybrid");

        String expected = "Hybrid";
        String actual = bus.getFuelType();
        assertEquals(driver_1, bus.getDriver());
        assertEquals(expected, actual);
    }

    /**
     * Test B5 on driver heavy licence, on hybrid bus
     */
    @Test
    public void test_bus_validLicenceOnBus_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 0, "Heavy");
        Bus bus = new Bus("12345678", driver_1, 60, 1.0, "hybrid");

        String expected = "Hybrid";
        String actual = bus.getFuelType();
        assertEquals(driver_1, bus.getDriver());
        assertEquals(expected, actual);
    }

    /**
     * Test B5 on driver medium licence, on electric bus
     */
    @Test
    public void test_bus_invalidLicenceForElectricity_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Medium");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Bus bus = new Bus("12345678", driver_1, 60, 1.0, "electricity");
        });

        String expected = "Driver Licence Restriction: Only drivers holding a Heavy or Public Transport licence are permitted to operate electric and hybrid buses.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    /**
     * Test B5 on driver public transport licence, on mispelled fueltype
     */
    @Test
    public void test_bus_mispelledFuelTypeWIthValidLicence_IllegalArgumentException() {
        Driver driver_1 = new Driver("08-08-2008", 6, "Heavy");
        assertThrows(IllegalArgumentException.class, () -> {
            Bus bus = new Bus("12345678", driver_1, 60, 1.0, "hybird");
        });

    }

}
