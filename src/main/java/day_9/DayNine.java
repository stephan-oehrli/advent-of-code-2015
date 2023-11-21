package day_9;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.*;

public class DayNine {

    public static void main(String[] args) throws FileNotFoundException {
        LocationGraph locationGraph = LocationGraph.of(FileUtil.readToList("day_9.txt"));
        System.out.println("Shortest distance: " + locationGraph.calculateShortestDistance());
        System.out.println("Longest distance: " + locationGraph.calculateLongestDistance());
    }

    @Getter(AccessLevel.PACKAGE)
    public static class LocationGraph {
        private final List<Location> locations = new ArrayList<>();
        private final HashMap<Location, List<Connection>> graph = new HashMap<>();

        private LocationGraph() {
        }

        public static LocationGraph of(List<String> inputs) {
            LocationGraph locationGraph = new LocationGraph();
            inputs.forEach(locationGraph::processInput);
            return locationGraph;
        }

        private void processInput(String input) {
            input = StringUtils.remove(input, "to ");
            input = StringUtils.remove(input, "= ");
            String[] split = input.split(" ");
            Location locationA = addLocation(new Location(split[0]));
            Location locationB = addLocation(new Location(split[1]));
            addConnection(locationA, locationB, Integer.parseInt(split[2]));
            addConnection(locationB, locationA, Integer.parseInt(split[2]));
        }

        private void addConnection(Location locationA, Location locationB, int distance) {
            if (!graph.containsKey(locationA)) {
                graph.put(locationA, new ArrayList<>());
            }
            graph.get(locationA).add(new Connection(locationB, distance));
        }

        private Location addLocation(Location location) {
            if (locations.contains(location)) {
                return locations.get(locations.indexOf(location));
            }
            locations.add(location);
            return location;
        }

        public int calculateShortestDistance() {
            List<Integer> shortestDistances = new ArrayList<>();
            for (Location location : locations) {
                shortestDistances.add(getDistanceOf(location, DistanceType.SHORTEST));
            }
            return Collections.min(shortestDistances);
        }

        public int calculateLongestDistance() {
            List<Integer> longestDistance = new ArrayList<>();
            for (Location location : locations) {
                longestDistance.add(getDistanceOf(location, DistanceType.LONGEST));
            }
            return Collections.max(longestDistance);
        }

        private int getDistanceOf(Location location, DistanceType distanceType) {
            int distance = 0;
            Location nextLocation = location;
            nextLocation.setVisited(true);
            while (!isEveryLocationVisited()) {
                Connection nearestConnection = getConnection(nextLocation, distanceType);
                distance += nearestConnection.distance();
                nextLocation = nearestConnection.to;
                nextLocation.setVisited(true);
            }
            locations.forEach(Location::reset);
            return distance;
        }

        private boolean isEveryLocationVisited() {
            for (Location location : locations) {
                if (!location.isVisited()) {
                    return false;
                }
            }
            return true;
        }

        private Connection getConnection(Location location, DistanceType distanceType) {
            if (distanceType == DistanceType.SHORTEST) {
                return getShortestConnection(location);
            }
            if (distanceType == DistanceType.LONGEST) {
                return getLongestConnection(location);
            }
            throw new IllegalStateException("Invalid distance type: " + distanceType.name());
        }

        private Connection getShortestConnection(Location location) {
            return graph.get(location).stream()
                    .filter(connection -> !connection.to().isVisited())
                    .min(Comparator.comparingInt(con -> con.distance))
                    .orElseThrow();
        }

        private Connection getLongestConnection(Location location) {
            return graph.get(location).stream()
                    .filter(connection -> !connection.to().isVisited())
                    .max(Comparator.comparingInt(con -> con.distance))
                    .orElseThrow();
        }

        private enum DistanceType {
            SHORTEST, LONGEST
        }
    }

    @Getter
    @RequiredArgsConstructor
    @EqualsAndHashCode
    public static class Location {
        private final String name;

        @Setter
        @EqualsAndHashCode.Exclude
        private boolean visited = false;

        public void reset() {
            visited = false;
        }
    }

    public record Connection(Location to, int distance) {
    }
}
