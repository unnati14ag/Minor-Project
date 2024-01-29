import java.io.*;
import java.util.*;

public class Routing {

    static HashMap<String, HashMap<String, Integer>> routeMap;

    public Routing() {
        routeMap = new HashMap<>();
    }

    public void addStation(String destinationName) {
        routeMap.put(destinationName, new HashMap<>());
    }

    public void addConnection(String source, String destination, int distance) {
        if (!routeMap.containsKey(source) || !routeMap.containsKey(destination)) {
            System.out.println("Invalid destination names.");
            return;
        }
        routeMap.get(source).put(destination, distance);
        routeMap.get(destination).put(source, distance);
    }

    public void displayStations() {
        System.out.println("List of Destinations:");
        routeMap.keySet().forEach(station -> System.out.println(station));
    }

    public void displayrouteMap() {
        System.out.println("Route Map:");
        routeMap.forEach((station, connections) -> {
            System.out.println(station + " =>");
            connections.forEach((destination, distance) -> System.out.printf("\t%s (Distance: %d kilometers)%n",
                    destination, distance));
        });
    }

    public int path(String source, String destination) {
        if (!routeMap.containsKey(source) || !routeMap.containsKey(destination)) {
            System.out.println("Invalid destination names.");
            return -1;
        }

        Map<String, Integer> distances = new HashMap<>();
        Set<String> unvisited = new HashSet<>(routeMap.keySet());

        for (String destinationName : unvisited) {
            distances.put(destinationName, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        while (!unvisited.isEmpty()) {
            String current = unvisited.stream()
                    .min(Comparator.comparingInt(distances::get))
                    .orElse(null);

            if (current == null) {
                System.out.println("No path found.");
                return -1;
            }

            unvisited.remove(current);

            routeMap.get(current).forEach((neighbor, distance) -> {
                int newDistance = distances.get(current) + distance;
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                }
            });
        }
        return distances.get(destination);
    }

    public static void main(String[] args) throws IOException {
        Routing routeNetwork = new Routing();
        routeNetwork.addStation("ISBT");
        routeNetwork.addStation("Jolly Grant Airport");
        routeNetwork.addStation("Kargi Chowk");
        routeNetwork.addStation("Rispana Pull");
        routeNetwork.addStation("Clock Tower");
        routeNetwork.addStation("Jogiwala");
        routeNetwork.addStation("Ballupur Chowk");
        routeNetwork.addStation("IMA");
        routeNetwork.addStation("FRI");
        routeNetwork.addStation("Pacific Mall");
        routeNetwork.addStation("Prem Nagar");
        routeNetwork.addStation("Survey Chowk");
        routeNetwork.addStation("Sai Mandir");
        routeNetwork.addStation("IT Park");
        routeNetwork.addStation("Railway Station");
        routeNetwork.addStation("Musoorie Diversion");
        routeNetwork.addStation("Balliwala Chowk");

        routeNetwork.addConnection("ISBT", "Prem Nagar", 9);
        routeNetwork.addConnection("ISBT", "Kargi Chowk", 3);
        routeNetwork.addConnection("ISBT", "Balliwala Chowk", 6);
        routeNetwork.addConnection("Prem Nagar", "IMA", 2);
        routeNetwork.addConnection("IMA", "FRI", 2);
        routeNetwork.addConnection("IMA", "Balliwala Chowk", 3);
        routeNetwork.addConnection("FRI", "Ballupur Chowk", 2);
        routeNetwork.addConnection("FRI", "Balliwala Chowk", 2);
        routeNetwork.addConnection("Ballupur Chowk", "Balliwala Chowk", 2);
        routeNetwork.addConnection("Ballupur Chowk", "Clock Tower", 4);
        routeNetwork.addConnection("Balliwala Chowk", "Sai Mandir", 2);
        routeNetwork.addConnection("Clock Tower", "Survey Chowk", 2);
        routeNetwork.addConnection("Clock Tower", "Pacific Mall", 6);
        routeNetwork.addConnection("Clock Tower", "IT Park", 8);
        routeNetwork.addConnection("Clock Tower", "Sai Mandir", 2);
        routeNetwork.addConnection("Clock Tower", "Railway Station", 2);
        routeNetwork.addConnection("Clock Tower", "Rispana Pull", 5);
        routeNetwork.addConnection("Musoorie Diversion", "Pacific Mall", 1);
        routeNetwork.addConnection("Musoorie Diversion", "IT Park", 3);
        routeNetwork.addConnection("IT Park", "Survey Chowk", 6);
        routeNetwork.addConnection("Survey Chowk", "Rispana Pull", 5);
        routeNetwork.addConnection("Sai Mandir", "Railway Station", 2);
        routeNetwork.addConnection("Railway Station", "Kargi Chowk", 4);
        routeNetwork.addConnection("Railway Station", "Rispana Pull", 4);
        routeNetwork.addConnection("Kargi Chowk", "Rispana Pull", 4);
        routeNetwork.addConnection("Rispana Pull", "Jogiwala", 2);
        routeNetwork.addConnection("Jogiwala", "Jolly Grant Airport", 22);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nMenu:");
            System.out.println("1. List all destinations");
            System.out.println("2. Show map");
            System.out.println("3. Get shortest distance");
            System.out.println("4. Exit");
            System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 4) : ");

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    routeNetwork.displayStations();
                    break;
                case 2:
                    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    routeNetwork.displayrouteMap();
                    break;
                case 3:
                    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.print("Enter source station: ");
                    String source = reader.readLine();
                    System.out.print("Enter destination station: ");
                    String destination = reader.readLine();
                    int distance = routeNetwork.path(source, destination);
                    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    if (distance != -1) {
                        System.out.println("Shortest distance: " + distance + " Kilometer");
                    }
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}