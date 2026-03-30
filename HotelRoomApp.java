/**
 * HotelRoomApp.java
 * 
 * This program demonstrates object modeling using abstraction,
 * inheritance, and polymorphism for a Hotel Booking system.
 * 
 * It creates different room types and displays their details
 * along with availability.
 * 
 * @author Md Nayaj Mondal
 * @version 1.0
 */

// Abstract class representing a general Room
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Common method to display basic details
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }

    // Abstract method (can be customized later if needed)
    public abstract void roomFeature();
}


// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    @Override
    public void roomFeature() {
        System.out.println("Feature: Suitable for one person.");
    }
}


// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    @Override
    public void roomFeature() {
        System.out.println("Feature: Ideal for two people.");
    }
}


// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    @Override
    public void roomFeature() {
        System.out.println("Feature: Luxury room with premium amenities.");
    }
}


// Main application class
public class HotelRoomApp {

    /**
     * Entry point of the application
     */
    public static void main(String[] args) {

        System.out.println("=== Hotel Room Availability ===\n");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Single Room details
        single.displayDetails();
        single.roomFeature();
        System.out.println("Available: " + singleAvailable);
        System.out.println("---------------------------");

        // Display Double Room details
        doubleRoom.displayDetails();
        doubleRoom.roomFeature();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("---------------------------");

        // Display Suite Room details
        suite.displayDetails();
        suite.roomFeature();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("---------------------------");

        System.out.println("\nApplication finished.");
    }
}
