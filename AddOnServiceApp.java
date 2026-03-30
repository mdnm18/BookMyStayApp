/**
 * AddOnServiceApp.java
 *
 * Demonstrates extending booking with optional services using:
 * - Map<String, List<Service>>
 * - Composition (not inheritance)
 * - Cost aggregation
 *
 * Core booking & inventory remain untouched.
 *
 * @author Md Nayaj Mondal
 * @version 6.0
 */

import java.util.*;

// ----------- Add-On Service -----------

class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}


// ----------- Add-On Service Manager -----------

class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName() +
                " to Reservation: " + reservationId);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        double total = 0.0;

        for (Service s : getServices(reservationId)) {
            total += s.getPrice();
        }

        return total;
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        List<Service> services = getServices(reservationId);

        System.out.println("\nAdd-On Services for " + reservationId + ":");

        if (services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getPrice() + ")");
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}


// ----------- Main Application -----------

public class AddOnServiceApp {

    public static void main(String[] args) {

        // Assume these reservation IDs already exist (from booking system)
        String res1 = "SINGLEROOM-1";
        String res2 = "SUITEROOM-1";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create available services
        Service breakfast = new Service("Breakfast", 500);
        Service wifi = new Service("Premium WiFi", 300);
        Service pickup = new Service("Airport Pickup", 800);

        // Guest selects services
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);

        manager.addService(res2, pickup);
        manager.addService(res2, breakfast);

        // Display services and cost
        manager.displayServices(res1);
        manager.displayServices(res2);

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}
