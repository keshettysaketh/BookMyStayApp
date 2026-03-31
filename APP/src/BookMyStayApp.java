import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

class SearchService {
    private RoomInventory inventory;
    private HashMap<String, Room> roomDetails;

    public SearchService(RoomInventory inventory, HashMap<String, Room> roomDetails) {
        this.inventory = inventory;
        this.roomDetails = roomDetails;
    }

    public void searchAvailableRooms() {
        System.out.println("\nAvailable Rooms:");

        for (String type : inventory.getAllInventory().keySet()) {
            int available = inventory.getAvailability(type);

            if (available > 0 && roomDetails.containsKey(type)) {
                Room room = roomDetails.get(type);
                System.out.println(type + " | Price: " + room.getPrice() + " | Available: " + available);
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 3);

        HashMap<String, Room> roomDetails = new HashMap<>();
        roomDetails.put("Single", new Room("Single", 2000));
        roomDetails.put("Double", new Room("Double", 3500));
        roomDetails.put("Suite", new Room("Suite", 5000));

        SearchService searchService = new SearchService(inventory, roomDetails);

        searchService.searchAvailableRooms();
    }
}