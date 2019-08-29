package edu.eloquenza.textadventure;

public class Main {

    public static void main(String[] args) {
        Game newGame = new Game(System.out, System.in);
        newGame.start();
    }
}
