package edu.eloquenza.textadventure;

import java.util.List;
import java.util.Objects;

public class Location {

    private String name;
    private List<String> itemsToBeFound;

    public Location(String name, List<String> items) {
        this.name = name;
        this.itemsToBeFound = items;
    }

    public String getName() {
        return name;
    }

    public List<String> getItemsToBeFound() {
        return itemsToBeFound;
    }

    public boolean removeItem(String itemName) {
        return itemsToBeFound.remove(itemName);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return name.equals(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
