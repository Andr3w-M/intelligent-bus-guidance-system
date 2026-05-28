package edu.rmit;
public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;
    private String address;
    private String birthdate;

    public Driver(String driverID, String name, int experienceYears,
                  String licenseType, String address, String birthdate) {
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    // D1: Check if the driverID is valid
    public static void validateDriverID(String driverID) {

        // Check if driverID is null or not exactly 10 characters
        if (driverID == null || driverID.length() != 10) {
            throw new IllegalArgumentException("driverID must be exactly 10 characters long.");
        }

        // Check if the first character is a digit between 2 and 9
        char firstChar = driverID.charAt(0);
        if (firstChar < '2' || firstChar > '9') {
            throw new IllegalArgumentException("First character of driverID must be a digit between 2 and 9.");
        }

        // Check if the second character is a digit between 2 and 9
        char secondChar = driverID.charAt(1);
        if (secondChar < '2' || secondChar > '9') {
            throw new IllegalArgumentException("Second character of driverID must be a digit between 2 and 9.");
        }

        // Check if the last two characters are uppercase letters
        char secondLastChar = driverID.charAt(8);
        char lastChar = driverID.charAt(9);

        if (!Character.isUpperCase(secondLastChar) || !Character.isLetter(secondLastChar)) {
            throw new IllegalArgumentException("Second-to-last character of driverID must be an uppercase letter.");
        }
        if (!Character.isUpperCase(lastChar) || !Character.isLetter(lastChar)) {
            throw new IllegalArgumentException("Last character of driverID must be an uppercase letter.");
        }

        // Count how many special characters exist between index 2 and 7
        int numberOfSpecialChars = 0;
        for (int i = 2; i <= 7; i++) {
            char currentChar = driverID.charAt(i);
            boolean isLetter = Character.isLetter(currentChar);
            boolean isDigit = Character.isDigit(currentChar);

            if (!isLetter && !isDigit) {
                numberOfSpecialChars = numberOfSpecialChars + 1;
            }
        }

        if (numberOfSpecialChars < 2) {
            throw new IllegalArgumentException("driverID must have at least 2 special characters between positions 3 and 8.");
        }
    }

    // D2: Check if the address is valid
    public static void validateAddress(String address) {

        if (address == null) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }

        String[] parts = address.split("\\|", -1);

        // D2: There must be exactly 5 parts separated by '|'
        if (parts.length != 5) {
            throw new IllegalArgumentException("Address must have exactly 5 parts");
        }
    }

    // D3: Check if the birthdate is valid

    // Validates birthdate format — must match DD-MM-YYYY
    public static void validateBirthdate(String birthdate) {

        if (birthdate == null) {
            throw new IllegalArgumentException(
                "Birthdate cannot be null.");
        }

        boolean matchesPattern = birthdate.matches("\\d{2}-\\d{2}-\\d{4}");
        if (!matchesPattern) {
            throw new IllegalArgumentException(
                "Birthdate must be in DD-MM-YYYY format.");
        }
    }

    // D4 and D5: Update driver information
    public void update(int newExperienceYears, String newLicenseType,
                       String newAddress, String newBirthdate) {

        // Check if licenseType is being changed for a driver with more than 10 years
        boolean isLicenseBeingChanged = !this.licenseType.equals(newLicenseType);
        boolean hasMoreThan10Years = this.experienceYears > 10;

        if (hasMoreThan10Years && isLicenseBeingChanged) {
            throw new IllegalArgumentException(
                "Cannot change licenseType for a driver with more than 10 years of experience.");
        }

        // Validate the new address (D2)
        validateAddress(newAddress);

        // Validate the new birthdate (D3)
        validateBirthdate(newBirthdate);

        // All checks passed — apply the updates
        this.experienceYears = newExperienceYears;
        this.licenseType = newLicenseType;
        this.address = newAddress;
        this.birthdate = newBirthdate;
    }

    public String getDriverID() {
        return driverID;
    }

    public String getName() {
        return name;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    // Converts driver data into a comma-separated line for TXT file storage
    public String toFileString() {
        String line = driverID + "," + name + "," + experienceYears + ","
                    + licenseType + "," + address + "," + birthdate;
        return line;
    }
    @Override
    public String toString() {
        return "Driver{driverID='" + driverID + "', name='" + name +
               "', experienceYears=" + experienceYears + ", licenseType='" + licenseType +
               "', address='" + address + "', birthdate='" + birthdate + "'}";
    }

    public static Driver fromFileString(String line) {

        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(",", 6);

        if (parts.length != 6) {
            return null;
        }

        try {
            String driverID       = parts[0];
            String name           = parts[1];
            int experienceYears   = Integer.parseInt(parts[2].trim());
            String licenseType    = parts[3];
            String address        = parts[4];
            String birthdate      = parts[5];

            return new Driver(driverID, name, experienceYears, licenseType, address, birthdate);

        } catch (NumberFormatException e) {
            return null;
        }
    }
}
