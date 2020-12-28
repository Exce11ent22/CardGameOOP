package cs.vsu.course2.cardgame.durak.game;

import java.util.*;

public class Game extends GameManager{

    private final Scanner in = new Scanner(System.in);

    public void run() {
        boolean running = true;
        while (running) {
            setup();
            game();
            System.out.println("The game has ended.");
            System.out.println("Play again? [Y/N]");

            boolean validResponse = false;
            while (!validResponse) {
                String response = in.nextLine().toLowerCase();
                switch (response) {
                    case "y" -> {
                        validResponse = true;
                        running = true;
                    }
                    case "n" -> {
                        validResponse = true;
                        running = false;
                    }
                    default -> validResponse = false;
                }
            }
        }
    }

}
