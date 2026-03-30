🏨 Hotel Booking System – Complete Learning README

📌 Overview

This project demonstrates the step-by-step evolution of a Hotel Booking System, starting from basic Java execution to a robust, scalable, and production-like backend design.

The focus is not just on coding, but on system design thinking, including:
	•	Object-oriented modeling
	•	Data structure usage
	•	Concurrency handling
	•	Error handling
	•	Persistence and recovery

⸻

🚀 System Evolution (Step-by-Step)

⸻

1. 🟢 Application Entry Point

Goal

Understand how a Java program starts and executes.

Key Concepts
	•	class as program container
	•	main() method as entry point
	•	static keyword
	•	Console output using System.out.println()

Outcome

A simple program that prints:
	•	Welcome message
	•	Application name
	•	Version

⸻

2. 🟡 Object Modeling (Abstraction & Inheritance)

Goal

Model real-world entities using OOP.

Key Concepts
	•	Abstract class → Room
	•	Inheritance → SingleRoom, DoubleRoom, SuiteRoom
	•	Polymorphism → using Room reference
	•	Encapsulation

Outcome

Structured representation of room types without focusing on data storage.

⸻

3. 🔵 Centralized Inventory (HashMap)

Goal

Replace scattered variables with a single source of truth.

Key Concepts
	•	HashMap<String, Integer>
	•	O(1) lookup
	•	Encapsulation of inventory logic
	•	Separation of concerns

Outcome

A centralized inventory system:

"Single Room" → 5
"Double Room" → 3


⸻

4. 🟣 Read-Only Search System

Goal

Allow users to view available rooms safely.

Key Concepts
	•	Read-only access
	•	Defensive programming
	•	Filtering unavailable rooms
	•	Separation of search from booking

Outcome

Guests can view only available rooms without modifying system state.

⸻

5. 🟠 Booking Request Queue (FIFO)

Goal

Handle multiple booking requests fairly.

Key Concepts
	•	Queue<Reservation>
	•	FIFO (First-Come-First-Served)
	•	Decoupling request intake from processing

Outcome

Requests are stored and processed in arrival order.

⸻

6. 🔴 Booking Engine (Safe Allocation)

Goal

Prevent double booking and ensure consistency.

Key Concepts
	•	Set<String> → unique room IDs
	•	Map<String, Set<String>> → allocation tracking
	•	Atomic operations
	•	Inventory synchronization

Outcome
	•	Unique room assignment
	•	No duplicate bookings
	•	Accurate inventory updates

⸻

7. 🟤 Add-On Services (Extensibility)

Goal

Add optional features without modifying core logic.

Key Concepts
	•	Composition over inheritance
	•	Map<String, List<Service>>
	•	One-to-many relationship
	•	Cost aggregation

Outcome

Guests can add services like:
	•	Breakfast
	•	WiFi
	•	Airport Pickup

⸻

8. ⚫ Booking History & Reporting

Goal

Track past bookings for analysis and auditing.

Key Concepts
	•	List<Reservation>
	•	Ordered storage
	•	Read-only reporting
	•	Separation of storage and reporting

Outcome
	•	Historical records
	•	Summary reports
	•	Audit trail

⸻

9. ⚠️ Validation & Error Handling

Goal

Ensure system reliability under invalid inputs.

Key Concepts
	•	Input validation
	•	Custom exceptions
	•	Fail-fast design
	•	Graceful error handling

Outcome
	•	Invalid inputs are rejected early
	•	System never crashes
	•	Clear error messages

⸻

10. 🔁 Cancellation & Rollback (Stack)

Goal

Safely reverse bookings.

Key Concepts
	•	State reversal
	•	Stack<String> (LIFO)
	•	Controlled mutation
	•	Validation before cancellation

Outcome
	•	Inventory restored correctly
	•	No duplicate cancellations
	•	Consistent rollback

⸻

11. ⚡ Concurrency Handling (Multi-threading)

Goal

Ensure correctness under multiple users.

Key Concepts
	•	Race conditions
	•	Thread safety
	•	synchronized keyword
	•	Critical sections

Outcome
	•	No double booking under concurrent access
	•	Safe shared resource handling

⸻

12. 💾 Persistence & Recovery

Goal

Make system state survive restarts.

Key Concepts
	•	Serialization & Deserialization
	•	File-based persistence
	•	Snapshot model (SystemState)
	•	Failure tolerance

Outcome
	•	Data saved to file
	•	System restores previous state on restart
	•	No data loss

⸻

🧠 Core Design Principles Learned

✅ Separation of Concerns

Each component has a clear responsibility:
	•	Inventory → state
	•	Booking → allocation
	•	Search → read-only
	•	Services → optional features

⸻

✅ Single Source of Truth
	•	Inventory stored in one place
	•	No duplicated state

⸻

✅ Fail-Fast & Defensive Design
	•	Errors detected early
	•	System protected from invalid operations

⸻

✅ Extensibility
	•	Add new features without changing core logic

⸻

✅ Consistency & Integrity
	•	No double booking
	•	Accurate inventory
	•	Safe rollback

⸻

✅ Concurrency Safety
	•	Multiple users handled correctly
	•	No race conditions

⸻

✅ Persistence Mindset
	•	Data survives restarts
	•	Foundation for database systems

⸻

🏗️ Final System Architecture

Guest → Search Service (Read-only)
      → Booking Queue (FIFO)
      → Booking Service (Allocation)
      → Inventory (State)
      → Add-On Services
      → Booking History
      → Reporting Service
      → Persistence Layer


⸻

🎯 What You Achieved

By completing this system, you have built:
	•	A complete backend simulation
	•	Strong understanding of OOP + DSA
	•	Real-world system design mindset
	•	Foundation for scalable applications

⸻

🚀 Future Enhancements
	•	Database integration (MySQL / MongoDB)
	•	REST API (Spring Boot)
	•	GUI or Web interface
	•	Authentication system
	•	Payment integration
	•	Cloud deployment

⸻

📌 Conclusion

This project is not just about a hotel booking system.
It is about learning how real systems are designed, built, and scaled.

You started with:
👉 Printing a message

And ended with:
👉 A concurrent, persistent, fault-tolerant system

⸻

👨‍💻 Author

Md Nayaj Mondal
B.Tech CSE Student
SRM Institute of Science and Technology
