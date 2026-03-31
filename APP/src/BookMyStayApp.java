import java.util.LinkedList;
import java.util.Queue;

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

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added: " + reservation.getGuestName() + " -> " + reservation.getRoomType());
    }

    public void viewQueue() {
        System.out.println("\nBooking Requests in Queue:");

        if (queue.isEmpty()) {
            System.out.println("No pending requests");
            return;
        }

        for (Reservation r : queue) {
            System.out.println(r.getGuestName() + " -> " + r.getRoomType());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Alice", "Single"));
        requestQueue.addRequest(new Reservation("Bob", "Suite"));
        requestQueue.addRequest(new Reservation("Charlie", "Double"));
        requestQueue.addRequest(new Reservation("David", "Single"));

        requestQueue.viewQueue();
    }
}