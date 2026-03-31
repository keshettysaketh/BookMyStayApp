public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Double", 0);

        BookingService service = new BookingService(inventory);

        service.processReservation(new Reservation("Alice", "Single"));
        service.processReservation(new Reservation("", "Single"));
        service.processReservation(new Reservation("Bob", "Suite"));
        service.processReservation(new Reservation("Charlie", "Double"));
    }
}

import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

class BookingHistory {
    private Map<String, Reservation> confirmed;
    private Set<String> cancelled;

    public BookingHistory() {
        confirmed = new HashMap<>();
        cancelled = new HashSet<>();
    }

    public void addReservation(Reservation r) {
        confirmed.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmed.get(id);
    }

    public boolean isCancelled(String id) {
        return cancelled.contains(id);
    }

    public void markCancelled(String id) {
        cancelled.add(id);
    }
}

class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        rollbackStack = new Stack<>();
    }

    public void cancel(String reservationId) {

        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        if (history.isCancelled(reservationId)) {
            System.out.println("Cancellation Failed: Already cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());

        inventory.increaseRoom(r.getRoomType());

        history.markCancelled(reservationId);

        System.out.println("Cancelled: " + reservationId + " | Room Released: " + rollbackStack.peek());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 0);
        inventory.addRoomType("Double", 1);

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("R1", "Alice", "Single", "S1"));
        history.addReservation(new Reservation("R2", "Bob", "Double", "D1"));

        CancellationService service = new CancellationService(inventory, history);

        service.cancel("R1");
        service.cancel("R1");
        service.cancel("R3");

        inventory.display();
    }
}