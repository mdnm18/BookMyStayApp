/**
 * BookingValidationApp.java
 *
 * Demonstrates:
 * - Input validation
 * - Custom exceptions
 * - Fail-fast design
 * - Safe error handling
 *
 * Ensures system stability under invalid inputs.
 *
 * @author Md Nayaj Mondal
 * @version 8.0
 */

import java.util.*;

// ----------- Custom Exceptions -----------

class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class NoAvailabilityException extends Exception {
    public NoAvailabilityException(String message) {
        super(message);
    }
}


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


// ----------- Inventory -----------

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) throws NoAvailabilityException {
        int current = getAvailability(roomType);

        if (current <= 0) {
            throw new NoAvailabilityException("No available rooms for: " + roomType);
        }

        inventory.put(roomType, current - 1);
    }
}


// ----------- Validator -----------

class BookingValidator {

    private RoomInventory inventory;

    public BookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Validate booking request (FAIL-FAST)
    public void validate(Reservation r)
            throws InvalidRoomTypeException, NoAvailabilityException {

        // Check room type validity
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidRoomTypeException(
                    "Invalid room type: " + r.getRoomType());
        }

        // Check availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new NoAvailabilityException(
                    "Room not available: " + r.getRoomType());
        }
    }
}


// ----------- Booking Service -----------

class BookingService {

    private RoomInventory inventory;
    private BookingValidator validator;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.validator = new BookingValidator(inventory);
    }

    public void process(Reservation r) {

        try {
            // Step 1: Validate input (fail-fast)
            validator.validate(r);

            // Step 2: Safe allocation
            inventory.decrement(r.getRoomType());

            System.out.println("Booking CONFIRMED for " + r.getGuestName()
                    + " (" + r.getRoomType() + ")");

        } catch (InvalidRoomTypeException | NoAvailabilityException e) {

            // Graceful failure handling
            System.out.println("Booking FAILED for " + r.getGuestName());
            System.out.println("Reason: " + e.getMessage());
        }
    }
}


// ----------- Main Application -----------

public class BookingValidationApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Test cases (valid + invalid)
        Reservation r1 = new Reservation("Amit", "Single Room");   // valid
        Reservation r2 = new Reservation("Riya", "Suite Room");    // no availability
        Reservation r3 = new Reservation("John", "Deluxe Room");   // invalid type

        service.process(r1);
        service.process(r2);
        service.process(r3);

        System.out.println("\nSystem continues running safely.");
    }
}
