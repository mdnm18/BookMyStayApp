/**
 * RoomInventoryApp.java
 *
 * Demonstrates centralized inventory management using HashMap.
 * Replaces scattered availability variables with a single source of truth.
 *
 * Focus:
 * - Encapsulation of inventory logic
 * - O(1) lookup using HashMap
 * - Separation of concerns
 *
 * @author Md Nayaj Mondal
 * @version 2.0
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Manager Class
class RoomInventory {

    // HashMap to store room type -> available count
    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (increase or decrease)
    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        inventory.put(roomType, current + change);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("=== Current Room Inventory ===");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}


// Main Application
public class RoomInventoryApp {

    public static void main(String[] args) {

        // Step 1: Initialize inventory system
        RoomInventory inventory = new RoomInventory();

        // Step 2: Display initial inventory
        inventory.displayInventory();

        System.out.println("\n--- Booking 1 Single Room ---");
        inventory.updateAvailability("Single Room", -1);

        System.out.println("\n--- Cancelling 1 Suite Room ---");
        inventory.updateAvailability("Suite Room", +1);

        // Step 3: Display updated inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}
