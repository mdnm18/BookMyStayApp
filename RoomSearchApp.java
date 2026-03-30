/**
 * RoomSearchApp.java
 *
 * Demonstrates read-only search functionality using:
 * - Centralized inventory (HashMap)
 * - Room domain objects
 * - Separation of concerns
 *
 * Guests can view available rooms without modifying system state.
 *
 * @author Md Nayaj Mondal
 * @version 3.0
 */

import java.util.*;

// ----------- Domain Model (Room) -----------

abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}


// ----------- Inventory (State Holder) -----------

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example: unavailable
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


// ----------- Search Service (Read-Only Logic) -----------

class RoomSearchService {

    private RoomInventory inventory;
    private List<Room> rooms;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;

        // Initialize room domain objects
        rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());
    }

    // Search available rooms (READ-ONLY)
    public void searchAvailableRooms() {

        System.out.println("=== Available Rooms ===\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}


// ----------- Main Application -----------

public class RoomSearchApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest performs search
        searchService.searchAvailableRooms();

        System.out.println("\nSearch completed. No data was modified.");
    }
}
