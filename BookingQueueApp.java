/**
 * BookingQueueApp.java
 *
 * Demonstrates handling multiple booking requests using a Queue.
 * Ensures fairness using FIFO (First-Come-First-Served).
 *
 * No inventory updates are performed at this stage.
 *
 * @author Md Nayaj Mondal
 * @version 4.0
 */

import java.util.*;

// ----------- Reservation (Request Model) -----------

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}


// ----------- Booking Request Queue -----------

class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all queued requests (without removing)
    public void displayQueue() {
        System.out.println("\n=== Booking Request Queue ===");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (FIFO head)
    public Reservation peekNext() {
        return queue.peek();
    }
}


// ----------- Main Application -----------

public class BookingQueueApp {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate guest booking requests
        bookingQueue.addRequest(new Reservation("Amit", "Single Room"));
        bookingQueue.addRequest(new Reservation("Riya", "Double Room"));
        bookingQueue.addRequest(new Reservation("John", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Sara", "Single Room"));

        // Display queue (preserves order)
        bookingQueue.displayQueue();

        // Show next request to be processed (without removing)
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            next.display();
        }

        System.out.println("\nRequests are queued. No rooms allocated yet.");
    }
}
