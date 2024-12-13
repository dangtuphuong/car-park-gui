import java.time.LocalDateTime;
/**
 * Class Car represents a car.
 *
 * Tu Phuong Dang (103814482)
 * @version 11 May, 2024
 */
public class Car
{
    // instance variables
    private String registrationNumber;
    private String make;
    private String model;
    private int year;
    private LocalDateTime parkingDateTime;

    /**
     * Constructor for objects of class Car
     * @param registrationNumber Car registration number
     * @param make Car make
     * @param model Car model
     * @param year Car year
     */
    public Car(String registrationNumber, String make, String model, int year, LocalDateTime parkingDateTime) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.parkingDateTime = parkingDateTime;
    }
    
    /**
     * Gets the registration number of the car.
     * @return The car's registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    /**
     * Gets the maker of the car.
     * @return The car's make
     */
    public String getMake() {
        return make;
    }
    
    /**
     * Gets the model of the car.
     * @return The car's model
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Gets the year of the car.
     * @return The car's year
     */
    public int getYear() {
        return year;
    }
    
    /**
     * Gets the parking time of the car.
     * @return The car's parking time
     */
    public LocalDateTime getParkingDateTime() {
        return parkingDateTime;
    }
}
