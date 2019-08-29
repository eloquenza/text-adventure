package edu.eloquenza.textadventure;

public class Enemy {

    private String name;
    private double health;
    private double attackValue;

    public Enemy() {
        this.name = "Reisender in lumpiger Klamotte";
        this.health = GameInfo.DEFAULT_START_HEALTH_POINTS;
        this.attackValue = GameInfo.BASE_ATTACK_VALUE;
    }

    public double getAttackValue() {
        return attackValue;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
