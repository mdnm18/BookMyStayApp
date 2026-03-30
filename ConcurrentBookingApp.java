/**
 * ConcurrentBookingApp.java
 *
 * Demonstrates:
 * - Race conditions vs synchronized access
 * - Thread-safe booking with critical sections
 * - Shared queue + shared inventory
 *
 * @author Md Nayaj Mondal
 * @version 10.0
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


// ----------- Thread-Safe Inventory -----------

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1); // Only 1 room to force conflict
    }

    // Critical section (ONLY one thread at a time)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            return false;
        }

        // Simulate delay (to expose race condition if unsynchronized)
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        inventory.put(roomType, available - 1);
        return true;
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}


// ----------- Shared Booking Queue -----------

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void add(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNext() {
        return queue.poll();
    }
}


// ----------- Booking Processor (Thread) -----------

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // Safely fetch request
            synchronized (queue) {
                r = queue.getNext();
            }

            if (r == null) break;

            // Critical section (allocation)
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " CONFIRMED booking for " + r.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " FAILED booking for " + r.getGuestName());
            }
        }
    }
}


// ----------- Main Application -----------

public class ConcurrentBookingApp {

    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple guests (same room → conflict)
        queue.add(new Reservation("Amit", "Single Room"));
        queue.add(new Reservation("Riya", "Single Room"));
        queue.add(new Reservation("John", "Single Room"));

        // Multiple threads processing bookings
        BookingProcessor t1 = new BookingProcessor(queue, inventory, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(queue, inventory, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.display();

        System.out.println("\nSystem remained consistent under concurrency.");
    }
}
