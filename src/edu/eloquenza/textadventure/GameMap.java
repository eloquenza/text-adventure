package edu.eloquenza.textadventure;

import java.util.*;

public class GameMap {

    private Map<Location, List<Location>> locationMap;
    public static Location DEFAULT_LOCATION;

    public GameMap(Random rand) {
        this.locationMap = setupMap(setupLocations(rand));
    }

    private Map<Location, List<Location>> setupMap(Location[] locations) {
        Comparator<Location> locationComparator = Comparator.comparing(Location::getName);
        Map<Location, List<Location>> map = new TreeMap<>(locationComparator);

        map.put(locations[0], Arrays.asList(locations[1]));
        map.put(locations[1], Arrays.asList(locations[0], locations[2], locations[3]));
        map.put(locations[2], Arrays.asList(locations[1], locations[3], locations[5]));
        map.put(locations[3], Arrays.asList(locations[1], locations[2], locations[4]));
        map.put(locations[4], Arrays.asList(locations[3], locations[5]));
        map.put(locations[5], Arrays.asList(locations[2], locations[4]));

        return map;
    }

    private Location[] setupLocations(Random rand) {
        Location[] locations = new Location[GameInfo.LOCATION_NAMES.length];
        List<String> items;

        for (int i = 0; i < GameInfo.LOCATION_NAMES.length; i++) {
            items = new ArrayList<>();
            // atleast 2, but up to 5 items (nextInt returns [0, 3))
            int numberOfItems = (GameInfo.MININUM_AMOUNT_OF_ITEMS_TO_BE_FOUND + rand.nextInt(4));
            for (int j = 0; j <= numberOfItems; j++) {
                int itemNameIndex = rand.nextInt(GameInfo.ITEM_NAMES.length);
                items.add(GameInfo.ITEM_NAMES[itemNameIndex]);
            }
            locations[i] = new Location(GameInfo.LOCATION_NAMES[i], items);
        }

        DEFAULT_LOCATION = locations[0];

        return locations;
    }

    public Location getLocationByName(String locationName) {
        for (Location loc: locationMap.keySet()) {
            if (loc.getName().equals(locationName)) {
                return loc;
            }
        }
        return null;
    }

    public boolean isPlayerAllowedToMoveThere(Location playerLocation, Location nextLocation) {
        return locationMap.get(playerLocation).contains(nextLocation);
    }

    public boolean didPlayerGoAShortcut(Location playerLocation, Location nextLocation) {
        if ((playerLocation.getName().equals("B") && nextLocation.getName().equals("D")) ||
                (playerLocation.getName().equals("D") && nextLocation.getName().equals("B")) ||
                (playerLocation.getName().equals("C") && nextLocation.getName().equals("F")) ||
                (playerLocation.getName().equals("F") && nextLocation.getName().equals("C"))) {
            return true;
        }
        return false;
    }

    public Set<Location> locations() {
        return locationMap.keySet();
    }
}