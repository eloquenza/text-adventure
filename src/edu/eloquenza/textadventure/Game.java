package edu.eloquenza.textadventure;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class Game {

    private PrintStream out;
    private InputStream in;
    private Scanner scanner;
    private Player player;
    private GameMap map;
    private Random rand;
    private double enemyProbability;

    public Game(PrintStream out, InputStream in) {
        this.out = out;
        this.in = in;
        this.scanner = new Scanner(this.in);

        this.rand = new Random();
        enemyProbability = rand.nextDouble();

        this.map = new GameMap(rand);
    }

    public void start() {
        setupPlayer();

        String reason = "";

        out.println("Willkommen in Aventurien, " + player.getName() + "!");
        while (true) {
            if (player.getHealth() < 0.0) {
                out.println("Sie sind gestorben.");
                reason = "Spieler gestorben";
                break;
            }

            out.println("Was wollen sie tun? (R)eisen, (S)uchen, (I)nventar oeffnen, Spiel (B)eenden?");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("B")) {
                reason = "Vom Spieler beendet.";
                break;
            }
            else if (choice.equals("R")) {
                town();
            }
            else if (choice.equals("S")) {
                search();
            }
            else if (choice.equals("I")) {
                listInventory();
            }
        }
        out.println("Spiel beendet! (Grund: " + reason + ")");
    }

    private void setupPlayer() {
        out.println("Wie heissen sie?");
        String playerName = scanner.nextLine();

        player = new Player(playerName, GameMap.DEFAULT_LOCATION);
    }

    private void town() {
        out.println("Ihr befindet euch in Ort " + player.getLocation() + ". Wohin moechtet ihr reisen?");

        String nextLocationName = scanner.nextLine().toUpperCase();
        Location nextLocation = map.getLocationByName(nextLocationName);

        if (nextLocation == null) {
            out.println("Dieser Ort existiert nicht, waehlen Sie bitte einen geeigneten Ort (" + map.locations() + ")");
            return;
        }

        if (map.isPlayerAllowedToMoveThere(player.getLocation(), nextLocation)) {
            out.println("Ihr seid erfolgreich von Ort " + player.getLocation() + " nach Ort " + nextLocation + " gereist. ");
            if (map.didPlayerGoAShortcut(player.getLocation(), nextLocation)) {
                if (rand.nextDouble() < enemyProbability) {
                    combat();
                }
            }
            player.setLocation(nextLocation);
        } else {
            out.println("Ihr koennt nicht von hier aus direkt nach Ort " + nextLocation + " reisen. Ihr verbleibt in Ort " + player.getLocation() + ". Bitte waehlt einen anderen Ort aus.");
        }
    }

    private void search() {
        List<String> itemsAtLocation = player.getLocation().getItemsToBeFound();
        if (itemsAtLocation.isEmpty()) {
            out.println("Ihr wuehlt durch das dichte Laub und findet nichts.");
            out.println("Dieser Ort wurde anscheinend bereits leergepluendert.");
        } else {
            String itemFound = getRandomItemFromInventory(itemsAtLocation);

            out.println("Ihr wuehlt durch das dichte Laub und findet " + itemFound + ". Tippe 'n', um den Gegenstand aufzunehmen. Tippe 's', um weiterzusuchen und den Gegenstand liegenzulassen.");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("N")) {
                pickUp(itemFound);
            } else if (choice.equals("S")) {
                search();
            }
        }
    }

    private void pickUp(String itemFound) {
        player.getLocation().removeItem(itemFound);
        if (player.addItem(itemFound)) {
            out.println("Ihr habt '" + itemFound + "' erfolgreich aufgenommen.");
        }
    }

    private void listInventory() {
        out.println(player.getInventory());
    }

    private void combat() {
        Enemy enemy = new Enemy();

        out.println("Ein " + enemy.getName() + " naehert sich Euch an. Ihr bereitet Euch auf einen Angriff vor.");
        out.println("Du hast " + player.getHealth() + " Leben, dein Gegner hat " + enemy.getHealth() + ".");
        out.println("Du hast einen Schadenswert von " + player.getAttackValue() + ", dein Gegner hat einen von " + enemy.getAttackValue() + ".");

        boolean didPlayerEquipItem = false;
        List<String> playerInventory = player.getInventory();

        if (!playerInventory.isEmpty()) {
            out.println("Moechten Sie einen Gegenstand ausruesten (Dadurch hast du 0.5 mehr Schadenswert)?");
            didPlayerEquipItem = scanner.nextLine().equals("y");

            if (didPlayerEquipItem) {
                String itemToEquip = getRandomItemFromInventory(playerInventory);
                out.println("Du hast '" + itemToEquip + "' ausgeruestet.");
            }
        }

        double playerAttackValue = didPlayerEquipItem ? (player.getAttackValue() + GameInfo.BONUS_ATTACK_VALUE) : player.getAttackValue();

        while (!isEntityDead(enemy.getHealth()) || !isEntityDead(player.getHealth())) {
            enemy.setHealth(enemy.getHealth() - playerAttackValue);
            out.println("Ihr attackiert den Gegner und verursacht " + playerAttackValue + " Schaden. Gegner HP: " + enemy.getHealth() + " Leben.");
            if (isEntityDead(enemy.getHealth())) {
                out.println("Du hast deinen Gegner erfolgreich besiegt.");
                break;
            }

            if (didPlayerDodge()) {
                player.setHealth(player.getHealth() - enemy.getAttackValue());
                out.println("Der Gegner attackiert Euch. Treffer. Eigene HP: " + player.getHealth());

                if (isEntityDead(player.getHealth())) {
                    out.println("Dein Gegner hat dich erfolgreich besiegt.");
                    break;
                }
            } else {
                out.println("Der Gegner attackiert Euch. Ihr weicht aus. Eigene HP: " + player.getHealth());
            }
        }
    }

    private boolean isEntityDead(double entityHealth) {
        return entityHealth <= GameInfo.DEFAULT_DEATH_HEALTH_POINTS;
    }

    private boolean didPlayerDodge() {
        return rand.nextDouble() < GameInfo.DODGE_PROBABILITY;
    }

    private String getRandomItemFromInventory(List<String> inventory) {
        int indexOfItem = rand.nextInt(inventory.size());
        return inventory.get(indexOfItem);
    }
}
