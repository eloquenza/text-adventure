package edu.eloquenza.textadventure;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private Location location;
    private List<String> inventory;
    private double health;
    private double attackValue;

    public Player(String name, Location location) {
        this.name = name;
        this.location = location;
        this.inventory = new ArrayList<>();
        this.health = GameInfo.DEFAULT_START_HEALTH_POINTS;
        this.attackValue = GameInfo.BASE_ATTACK_VALUE;
    }

    public double getAttackValue() {
        return attackValue;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean addItem(String item) {
        return inventory.add(item);
    }

    public List<String> getInventory() {
        return inventory;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
