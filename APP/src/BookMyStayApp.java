import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("State saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving state");
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("State loaded successfully");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        SystemState loadedState = service.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
        } else {
            inventory = new HashMap<>();
            inventory.put("Single", 5);
            inventory.put("Double", 3);

            bookings = new ArrayList<>();
            bookings.add(new Reservation("R1", "Alice", "Single"));
            bookings.add(new Reservation("R2", "Bob", "Double"));
        }

        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        System.out.println("\nBookings:");
        for (Reservation r : bookings) {
            System.out.println(r.getReservationId() + " | " + r.getGuestName() + " | " + r.getRoomType());
        }

        service.save(new SystemState(inventory, bookings));
    }
}