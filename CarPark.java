import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;
/**
 * Class CarPark is responsible for maintaining a list of all parking spots.
 *
 * Tu Phuong Dang (103814482)
 * @version 11 May, 2024
 */
public class CarPark
{
    // instance variables
    private ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();

    /**
     * Adds a parking spot.
     * @param spotID Spot ID to be added
     * @return result message
     */
    public String addParkingSpot(String spotID) {
        // Check if the spotId format is correct
        if (!spotID.matches("[A-Z]\\d{3}")) {
            return("Invalid slot ID format. Slot ID must be an uppercase letter followed by 3 digits.");
        }
        
        // Check if the spotId is unique
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotID().equals(spotID)) {
                return("Slot ID " + spotID + " already exists. Please choose a different Slot ID.");
            }
        }

        // Add spot to the list
        ParkingSpot spot = new ParkingSpot(spotID);
        parkingSpots.add(spot);
        return("OK");
    }
    
    /**
     * Deletes a parking spot .
     * @param spotID Spot ID to be deleted
     * @return result message
     */
    public String deleteParkingSpot(String spotID) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotID().equals(spotID)) {
                // Check if spot is occupied
                if (spot.getIsOccupied()) {
                    return("Slot " + spotID + " is occupied.");
                } else {
                    parkingSpots.remove(spot);
                    return("OK");
                }
            }
        }
        return("Slot " + spotID + " does not exist.");
    }

    /**
     * Gets the list of occupied spots.
     * @return List of occupied spots
     */
    public ArrayList<ParkingSpot> getOccupiedSpots() {
        ArrayList<ParkingSpot> occupiedSpotsList = new ArrayList<>();
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied()) {
                occupiedSpotsList.add(spot);
            }
        }
        return occupiedSpotsList;
    }
    
    /**
     * Gets the parking time length.
     * @return the parking time length
     */
    public String getParkingDuration(LocalDateTime parkingDateTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(parkingDateTime, currentTime);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }
    
    /**
     * Lists all parking spots.
     * @return result message
     */
    public String listAllSpots() {
        // Display list of spots, whether occupied, and parked car information
        int totalSpots = parkingSpots.size();
        ArrayList<ParkingSpot> occupiedSpotsList = getOccupiedSpots();
        int occupiedSpots = occupiedSpotsList.size();
        int unoccupiedSpots = totalSpots - occupiedSpots;
        
        // Interate though all parkingSpots to return information about each spot
        String result = "List of all parking slots\n--------------\n";
        
        for (ParkingSpot spot : parkingSpots) {
            result += spot.getSpotID() + ", " + (spot.getIsOccupied() ? "Occupied" : "Unoccupied") + (spot.getIsOccupied() ? " (Registration: " + spot.getParkedCar().getRegistrationNumber() + ", Duration: " + getParkingDuration(spot.getParkedCar().getParkingDateTime()) + ")" : "") + "\n";
        }
        
        result += "\n\nSummary\n--------------\n";
        result += "Total Slots: " + totalSpots + "\n";
        result += "Occupied Slots: " + occupiedSpots + "\n";
        result += "Unoccupied Slots: " + unoccupiedSpots + "\n";
        
        return result;
    }

    /**
     * Parks a car.
     * @param spotID Spot ID where the car will be parked
     * @param registrationNumber Car registration number
     * @param make Car make
     * @param model Car model
     * @param year Car year
     * @return result message
     */
    public String parkCar(String spotID, String registrationNumber, String make, String model, int year, LocalDateTime parkingDateTime) {
        // Check if the car registration number format is correct
        if (!registrationNumber.matches("[A-Z]\\d{4}")) {
            return("Invalid car registration number format. Car registration number must be an uppercase letter followed by 4 digits.");
        }
        
        // Check if the car year is within the valid range
        if (year < 2004 || year > 2024) {
            return("Car year must be between 2004 and 2024.");
        }
        
        // Check if the car is already parked
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied() && spot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
                return("Car is already parked in spot " + spot.getSpotID() + ".");
            }
        }
        
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotID().equals(spotID)) {
                // Check if spot is unoccupied
                if (spot.getIsOccupied()) {
                    return("Slot " + spotID + " is occupied.");
                } else {
                    // Create Car object and park it in the spot
                    Car car = new Car(registrationNumber, make, model, year, parkingDateTime);
                    spot.setParkedCar(car);
                    spot.setIsOccupied(true);
                    return("OK");
                }
            }
        }
        
        // Display spot ID not found
        return("Slot " + spotID + " does not exist.");
    }

    /**
     * Finds a car by its registration number.
     * @param registrationNumber Car registration number to search for
     * @return result message
     */
    public String findCarByRegistration(String registrationNumber) {
        // Find car by registration number and display spot information
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied() && spot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
                return("Car with registration [" + registrationNumber + "] found on spot [" + spot.getSpotID() + "]. Duration: " + getParkingDuration(spot.getParkedCar().getParkingDateTime()));
            }
        }
        
        // Display car not found by given registration number
        return("Car with registration [" + registrationNumber + "] is not parked here!");
    }

    /**
     * Removes a car by its registration number.
     * @param registrationNumber Car registration number to remove
     * @return parked car spot object
     */
    public ParkingSpot removeCarByRegistration(String registrationNumber) {
        // Remove car from the spot by registration number
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied() && spot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
                spot.setParkedCar(null);
                spot.setIsOccupied(false);
                return spot;
            }
        }
        
        // Display car not found by given registration number
        //return("Car with registration [" + registrationNumber + "] is not parked here!");
        return null;
    }

    /**
     * Finds cars by make.
     * @param make Car make to search for
     * @return result message
     */
    public String findCarsByMake(String make) {
        int count = 0;
        String message = "";
        // Find cars by make and display slot and car information
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied() && spot.getParkedCar().getMake().equals(make)) {
                count++;
                message += ("Registration [" + spot.getParkedCar().getRegistrationNumber() + "] (" + spot.getParkedCar().getMake() + " " + spot.getParkedCar().getModel() + " " + spot.getParkedCar().getYear() + ") parked at spot [" + spot.getSpotID() + "]. Parking Duration: " + getParkingDuration(spot.getParkedCar().getParkingDateTime()) + "\n");
            }
        }
        String result = count + " car(s) with make [" + make + "] found\n---------------\n" + message;
        return count > 0 ? result : null;
    }
    
    /**
     * Gets number of car parked.
     * @return the number of cars
     */
    public int getParkedCarsCount() {
        int count = 0;
        // Remove all cars from the spots
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied()) {
                count++;
            }
        }

        return count;
    }

    /**
     * Resets the car park by removing all parked cars.
     */
    public void resetCarPark() {
        // Remove all cars from the spots
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getIsOccupied()) {
                spot.setParkedCar(null);
                spot.setIsOccupied(false);
            }
        }
    }
}
