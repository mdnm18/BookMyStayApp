/**
 * PersistenceApp.java
 *
 * Demonstrates:
 * - Serialization & Deserialization
 * - File-based persistence
 * - Recovery after restart
 * - Graceful failure handling
 *
 * Stores inventory + booking history.
 *
 * @author Md Nayaj Mondal
 * @version 11.0
 */

import java.io.*;
import java.util.*;

// ----------- Reservation -----------

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public void display() {
        System.out.println(guestName + " | " + roomType + " | " + roomId);
    }

    public String getRoomType() { return roomType; }
}


// ----------- Inventory -----------

class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}


// ----------- Booking History -----------

class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }

    public void display() {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }
}


// ----------- Wrapper (System State Snapshot) -----------

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}


// ----------- Persistence Service -----------

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    // Save state to file
    public void save(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("\nState saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("State loaded successfully.");
            return (SystemState) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No saved state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Corrupted data. Starting with clean state.");
        }

        // Fallback: return fresh state
        return new SystemState(new RoomInventory(), new BookingHistory());
    }
}


// ----------- Main Application -----------

public class PersistenceApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // STEP 1: Load previous state (if exists)
        SystemState state = persistence.load();

        RoomInventory inventory = state.inventory;
        BookingHistory history = state.history;

        // STEP 2: Simulate system running
        System.out.println("\n--- Current System State ---");
        inventory.display();
        history.display();

        // Simulate new booking
        System.out.println("\nAdding new booking...");
        Reservation r = new Reservation("Amit", "Single Room", "SINGLEROOM-3");
        history.add(r);

        // STEP 3: Save state before shutdown
        persistence.save(new SystemState(inventory, history));

        System.out.println("\nSystem shutting down safely...");
    }
}
