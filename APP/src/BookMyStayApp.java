abstract class Room {
    private String roomType;
    private int beds;
    private double price;
    private String size;

    public Room(String roomType, int beds, double price, String size) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.size = size;
    }

    public String getRoomType() { return roomType; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }
    public String getSize() { return size; }

    public void displayInfo() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size);
        System.out.println("Price per Night: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0, "200 sq ft");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0, "350 sq ft");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0, "600 sq ft");
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Book My Stay App!");
        System.out.println("Version: 2.0\n");

        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 5;
        int suiteRoomAvailability = 2;

        Room s = new SingleRoom();
        Room d = new DoubleRoom();
        Room suite = new SuiteRoom();

        s.displayInfo();
        System.out.println("Available: " + singleRoomAvailability + "\n");

        d.displayInfo();
        System.out.println("Available: " + doubleRoomAvailability + "\n");

        suite.displayInfo();
        System.out.println("Available: " + suiteRoomAvailability + "\n");
    }
}