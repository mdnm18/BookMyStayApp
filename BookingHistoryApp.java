/**
 * BookingHistoryApp.java
 *
 * Demonstrates historical tracking and reporting:
 * - Stores confirmed reservations in order
 * - Provides read-only reporting
 * - Maintains separation of storage and reporting logic
 *
 * @author Md Nayaj Mondal
 * @version 7.0
 */

import java.util.*;

// ----------- Reservation (Enhanced for History) -----------

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room: " + roomType +
                " | ID: " + roomId);
    }
}


// ----------- Booking History (State Storage) -----------

class BookingHistory {

    // List preserves insertion order
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Read-only access
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history);
    }
}


// ----------- Reporting Service -----------

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void showAllBookings() {
        System.out.println("\n=== Booking History ===");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : list) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary() {
        System.out.println("\n=== Booking Summary ===");

        Map<String, Integer> countByType = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            countByType.put(
                r.getRoomType(),
                countByType.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> e : countByType.entrySet()) {
            System.out.println(e.getKey() + " booked: " + e.getValue());
        }

        System.out.println("Total bookings: " + history.getAllReservations().size());
    }
}


// ----------- Main Application -----------

public class BookingHistoryApp {

    public static void main(String[] args) {

        // Initialize history system
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from booking engine)
        history.addReservation(new Reservation("Amit", "Single Room", "SINGLEROOM-1"));
        history.addReservation(new Reservation("Riya", "Double Room", "DOUBLEROOM-1"));
        history.addReservation(new Reservation("John", "Single Room", "SINGLEROOM-2"));
        history.addReservation(new Reservation("Sara", "Suite Room", "SUITEROOM-1"));

        // Admin uses reporting service
        BookingReportService reportService = new BookingReportService(history);

        reportService.showAllBookings();
        reportService.generateSummary();

        System.out.println("\nReports generated without modifying booking history.");
    }
}
