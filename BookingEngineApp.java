/**
 * BookingEngineApp.java
 *
 * Demonstrates safe booking confirmation with:
 * - FIFO queue processing
 * - Unique room allocation using Set
 * - Inventory synchronization
 *
 * Ensures no double-booking occurs.
 *
 * @author Md Nayaj Mondal
 * @version 5.0
 */

import java.util.*;

// ----------- Reservation -----------

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}


// ----------- Inventory Service -----------

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, getAvailability(roomType) - 1);
    }

    public void display() {
        System.out.println("\n=== Inventory Status ===");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}


// ----------- Booking Queue -----------

class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNext() {
        return queue.poll(); // FIFO removal
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


// ----------- Booking Service -----------

class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per room type
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String base = roomType.replace(" ", "").toUpperCase();

        int count = allocatedRooms
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        return base + "-" + count;
    }

    // Process booking request
    public void process(Reservation r) {

        String roomType = r.getRoomType();

        System.out.println("\nProcessing request for " + r.getGuestName());

        // Step 1: Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Booking FAILED: No rooms available.");
            return;
        }

        // Step 2: Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Step 3: Ensure Set exists
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());

        // Step 4: Prevent duplicate allocation
        Set<String> assigned = allocatedRooms.get(roomType);

        if (assigned.contains(roomId)) {
            System.out.println("ERROR: Duplicate room detected!");
            return;
        }

        // Step 5: Atomic allocation
        assigned.add(roomId);
        inventory.decrement(roomType);

        // Step 6: Confirmation
        System.out.println("Booking CONFIRMED");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
    }

    // Display allocated rooms
    public void showAllocations() {
        System.out.println("\n=== Allocated Rooms ===");

        for (Map.Entry<String, Set<String>> e : allocatedRooms.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}


// ----------- Main Application -----------

public class BookingEngineApp {

    public static void main(String[] args) {

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();
        BookingService service = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.add(new Reservation("Amit", "Single Room"));
        queue.add(new Reservation("Riya", "Single Room"));
        queue.add(new Reservation("John", "Single Room")); // Should fail
        queue.add(new Reservation("Sara", "Suite Room"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNext();
            service.process(r);
        }

        // Final state
        service.showAllocations();
        inventory.display();

        System.out.println("\nAll requests processed safely.");
    }
}
