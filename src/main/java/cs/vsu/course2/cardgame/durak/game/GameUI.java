package cs.vsu.course2.cardgame.durak.game;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.player.Player;

import java.util.Scanner;

public class GameUI {

    private GameManager gm;
    private final Scanner in = new Scanner(System.in);

    public GameUI(GameManager gm){
        this.gm = gm;
    }

    public void RoundData(){
        String roundName = "Round " + gm.getRound();
        String headerContent = "Attacker: " + gm.getAttacker() + " | " + "Defender: " + gm.getDefender() + "\n";
        System.out.println(headerContent);
        System.out.println(roundName + " has began!\n");
    }

    public Player setupPlayer(){
        System.out.print("Input player name: ");
        return new Player(in.nextLine());
    }

    public void gameEnded(){
        System.out.println("The winner is " + gm.getWinner() + "!\n");
    }

    public int playerInput(Player player, boolean roundInitiated) {
        boolean isAttacker = player.isAttacker();
        turnPrompt(player, roundInitiated);
        int playerSelection = -1;
        boolean properInput = false;
        while (!properInput) {
            playerSelection = in.nextInt();
            if (isAttacker) {
                if (roundInitiated) {
                    properInput = ((playerSelection >= 0) && (playerSelection <= player.cardsInHand()));
                } else {
                    properInput = ((playerSelection >= 1) && (playerSelection <= player.cardsInHand()));
                }
            } else {
                properInput = ((playerSelection >= 0) && (playerSelection <= player.cardsInHand()));
            }
            if (!properInput) {
                System.out.println("Invalid input. Please enter an acceptable value.");
            }
        }
        return playerSelection;
    }

    private void turnPrompt(Player player, boolean roundInitiated) {
        boolean isAttacker = player.isAttacker();

        String preContent = "Cards in a Deck: " + gm.getDeck().size() + "\n";
        preContent += "Cards in a Hand: " + player.cardsInHand() + "\n";
        String fieldString;
        String tail = "\t\t\t";

        String content = "Trump: " + gm.getTrump() + " " + gm.getTrump().getSimpleForm() + player.cardList();
        content += "=== OTHER OPTIONS ===\n";

        // Specified prompts for attacker and defender
        if (isAttacker) {
            if (roundInitiated) {
                preContent += "\tATTACK\n";
                content += "0 | Beaten\n";
            } else {
                preContent += "\tATTACK\n";
                content += "\n\n";
            }
            tail += player + ", you're attacking!\n";

        } else {
            preContent += "\tDEFENSE\n";
            content += "0 | Take\n";
            tail += player + ", you're defending!\n";
        }

        if (gm.getCurrentField() == null) {
            fieldString = "";
        } else {
            fieldString = "" + gm.getCurrentField();
        }
        System.out.println(fieldString + preContent + content + tail);
    }

    public void announceCardPlayed(Player player, Card card) {
        System.out.println(player + " has played " + card);
    }

}
