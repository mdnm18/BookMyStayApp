/**
 * BookingCancellationApp.java
 *
 * Demonstrates safe cancellation with:
 * - State reversal
 * - Stack-based rollback (LIFO)
 * - Inventory restoration
 * - Validation of cancellation requests
 *
 * @author Md Nayaj Mondal
 * @version 9.0
 */

import java.util.*;

// ----------- Reservation -----------

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    public boolean isActive() { return active; }
    public void cancel() { this.active = false; }

    public void display() {
        System.out.println(guestName + " | " + roomType + " | " + roomId +
                " | Status: " + (active ? "CONFIRMED" : "CANCELLED"));
    }
}


// ----------- Inventory -----------

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}


// ----------- Booking History -----------

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public Reservation findById(String roomId) {
        for (Reservation r : history) {
            if (r.getRoomId().equals(roomId)) {
                return r;
            }
        }
        return null;
    }

    public void displayAll() {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }
}


// ----------- Cancellation Service -----------

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String roomId) {

        System.out.println("\nProcessing cancellation for Room ID: " + roomId);

        // Step 1: Validate reservation
        Reservation r = history.findById(roomId);

        if (r == null) {
            System.out.println("Cancellation FAILED: Reservation not found.");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation FAILED: Already cancelled.");
            return;
        }

        // Step 2: Record for rollback (Stack)
        rollbackStack.push(roomId);

        // Step 3: Restore inventory
        inventory.increment(r.getRoomType());

        // Step 4: Update booking state
        r.cancel();

        // Step 5: Confirmation
        System.out.println("Cancellation SUCCESS for " + r.getGuestName());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}


// ----------- Main Application -----------

public class BookingCancellationApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Amit", "Single Room", "SINGLEROOM-1");
        Reservation r2 = new Reservation("Riya", "Double Room", "DOUBLEROOM-1");

        history.add(r1);
        history.add(r2);

        CancellationService cancelService =
                new CancellationService(inventory, history);

        // Perform cancellations
        cancelService.cancelBooking("SINGLEROOM-1"); // valid
        cancelService.cancelBooking("SINGLEROOM-1"); // duplicate cancel
        cancelService.cancelBooking("INVALID-ID");   // not found

        // Display final state
        history.displayAll();
        inventory.display();
        cancelService.showRollbackStack();

        System.out.println("\nSystem state restored safely.");
    }
}
