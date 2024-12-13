
/**
 * Class ParkingSpot represents a parking spot
 *
 * Tu Phuong Dang (103814482)
 * @version 11 May, 2024
 */
public class ParkingSpot
{
    // instance variables
    private String spotID;
    private boolean isOccupied;
    private Car parkedCar;

    /**
     * Constructor for objects of class ParkingSpot
     * @param spotId Spot ID
     */
    public ParkingSpot(String spotID) {
        this.spotID = spotID;
        this.isOccupied = false;
    }
    
    /**
     * Getter for spotID.
     * @return Spot ID
     */
    public String getSpotID() {
        return spotID;
    }
    
    /**
     * Checks if the parking spot is occupied.
     * @return True if occupied, false otherwise
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }
    
    // Setter for isOccupied
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    
    /**
     * Gets the car parked in the spot.
     * @return The parked car, or null if spot is unoccupied
     */
    public Car getParkedCar() {
        return parkedCar;
    }

    // Setter for parkedCar
    public void setParkedCar(Car car) {
        this.parkedCar = car;
    }
}
