package edu.eloquenza.textadventure;

public class GameInfo {

    public static final String[] ITEM_NAMES = {
            "spitzer Stein",
            "langer Stock",
            "Schwert",
            "Kuchen",
            "Bratpfanne",
            "Buch",
            "Apfel",
            "Birne",
            "Kartoffel",
            "Kaffeebohnen",
            "Papier"
    };
    public static final int MININUM_AMOUNT_OF_ITEMS_TO_BE_FOUND = 2;

    public static final String[] LOCATION_NAMES = {"A", "B", "C", "D", "E", "F"};

    public static final double DEFAULT_START_HEALTH_POINTS = 10.0;
    public static final double DEFAULT_DEATH_HEALTH_POINTS = 0.0;
    public static final double BASE_ATTACK_VALUE = 2.0;
    public static final double BONUS_ATTACK_VALUE = 0.5;
    public static final double DODGE_PROBABILITY = 0.2;
}
