import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }

        int available = inventory.get(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + type);
        }

        inventory.put(type, available - 1);
    }
}

class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory) throws InvalidBookingException {

        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (r.getRoomType() == null || r.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (inventory.getAvailability(r.getRoomType()) == -1) {
            throw new InvalidBookingException("Room type does not exist: " + r.getRoomType());
        }
    }
}

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {
        try {
            BookingValidator.validate(r, inventory);
            inventory.reduceRoom(r.getRoomType());
            System.out.println("Booking Confirmed: " + r.getGuestName() + " -> " + r.getRoomType());
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

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