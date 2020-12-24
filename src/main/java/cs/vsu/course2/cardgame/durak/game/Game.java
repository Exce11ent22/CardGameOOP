package cs.vsu.course2.cardgame.durak.game;

import cs.vsu.course2.cardgame.durak.database.JDBC;
import cs.vsu.course2.cardgame.durak.player.Player;
import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Suit;
import cs.vsu.course2.cardgame.durak.dealer.Dealer;
import cs.vsu.course2.cardgame.durak.deck.Deck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static Suit trump; //козырь
    private Player one, two, attacker, defender, winner, loser;
    private Deck deck;
    private int round;
    private Table currentField;
    private boolean roundInitiated;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String timeStart, timeEnd;
    private final JDBC DB = new JDBC();
    private final Scanner in = new Scanner(System.in);

    public void run() {
        boolean running = true;
        while (running) {
            timeStart = timeFormat.format(Calendar.getInstance().getTime());
            setup();
            game();
            timeEnd = timeFormat.format(Calendar.getInstance().getTime());
            DB.addSessionData(this);
            round = 1;
            System.out.println("The game has ended.");
            System.out.println("Play again? [Y/N]");

            boolean validResponse = false;
            while (!validResponse) {
                String response = in.nextLine().toLowerCase();
                switch (response) {
                    case "y":
                        validResponse = true;
                        running = true;
                        break;
                    case "n":
                        validResponse = true;
                        running = false;
                        break;
                    default:
                        validResponse = false;
                        break;
                }
            }
        }
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public int getRound() {
        return round;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    //for tests
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setTimeStart() {
        this.timeStart = timeFormat.format(Calendar.getInstance().getTime());
    }

    public void setTimeEnd() {
        this.timeEnd = timeFormat.format(Calendar.getInstance().getTime());
    }

    //initialization
    private void setup() {
        //first player
        System.out.print("Name of Player 1: ");
        one = new Player(in.nextLine());

        //second player
        System.out.print("Name of Player 2: ");
        two = new Player(in.nextLine());

        //deck
        deck = new Deck();

        //dealing cards
        Dealer.dealCards(one, two, deck);

        //determining trump
        trump = deck.determTrump();

        //round counter
        round = 1;
    }

    private void game() {
        //set attacker
        setAttacker(one);
        setDefender(two);

        boolean gameOver = false;
        while (!gameOver) {
            //round
            boolean thisRound = round();

            if (win()) {
                gameOver = true;
            } else {
                attacker.replenish(deck);
                defender.replenish(deck);
                round++;
                if (thisRound)
                    switchRoles();
            }
        }
        System.out.println("The winner is " + winner() + "!\n");
    }

    private boolean round() {
        String roundName = "Round " + round;
        String headerContent = "Attacker: " + attacker + " | " + "Defender: " + defender + "\n";
        roundInitiated = false;
        System.out.println(headerContent);
        System.out.println(roundName + " has began!\n");

        // Round start
        int attack = playerInput(attacker);
        Card attackerCard = attacker.useCard(attack);
        announceCardPlayed(attacker, attackerCard);

        // After every attack check for victory - mostly for the last round
        if (win()) {
            return true;
        }

        // Table generation
        Table roundField = new Table(attackerCard);
        currentField = roundField;
        roundInitiated = true;

        // Checks for the win
        while (!roundField.isCompleted()) {
            boolean defenderTurn = defenderResponse(roundField);
            if (defenderTurn || win()) {
                roundInitiated = false;
                currentField = null;
                return false;
            }
            boolean attackerTurn = attackerResponse(roundField);
            if (attackerTurn || win()) {
                roundInitiated = false;
                currentField = null;
                return true;
            }
        }
        return false;
    }

    private boolean win() {
        return winner() != null;
    }

    private Player winner() {
        if (one.cardsInHand() == 0 && deck.isEmpty()) {
            winner = one;
            loser = two;
            return one;
        }
        if (two.cardsInHand() == 0 && deck.isEmpty()) {
            winner = two;
            loser = one;
            return two;
        }
        return null;
    }

    private int playerInput(Player player) {
        boolean isAttacker = player.isAttacker();
        turnPrompt(player);
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

    private void turnPrompt(Player player) {
        boolean isAttacker = player.isAttacker();

        String preContent = "Cards in a Deck: " + deck.size() + "\n";
        preContent += "Cards in a Hand: " + player.cardsInHand() + "\n";
        String fieldString;
        String tail = "\t\t\t";

        String content = "Trump: " + trump + player.cardList();
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

        if (currentField == null) {
            fieldString = "";
        } else {
            fieldString = "" + currentField;
        }
        System.out.println(fieldString + preContent + content + tail);
    }

    private void announceCardPlayed(Player player, Card card) {
        System.out.println(player + " has played " + card);
    }

    private void setAttacker(Player p) {
        attacker = p;
        p.makeAttacker();
    }

    private void setDefender(Player p) {
        defender = p;
        p.makeDefender();
    }

    private void switchRoles() {
        Player temp = attacker;
        attacker = defender;
        defender = temp;
        one.switchRole();
        two.switchRole();
    }

    private boolean defenderResponse(Table table) {
        int defenderResponse = -1;
        boolean properDefenderResponse = false;
        while (!properDefenderResponse) {
            try {
                defenderResponse = playerInput(defender);
                if (defenderResponse != 0) {
                    Card defenderResponseCard = defender.getCard(defenderResponse);
                    table.respond(defenderResponseCard, trump);
                    properDefenderResponse = true;
                    defender.useCard(defenderResponse);
                    announceCardPlayed(defender, defenderResponseCard);
                    return false;
                } else {
                    properDefenderResponse = true;
                    System.out.println("\n" + defender +
                            " has chosen to take all cards in the field and end the round!");
                    ArrayList<Card> takenCards = table.fetchCards();
                    takenCards.forEach((card) -> {
                        defender.takeCard(card);
                    });
                    table.finish();
                    return true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid defender!");
                properDefenderResponse = false;
            }
        }
        return true;
    }

    private boolean attackerResponse(Table table) {
        int attackerResponse = -1;
        boolean properAttackerResponse = false;
        while (!properAttackerResponse) {
            try {
                attackerResponse = playerInput(attacker);
                if (attackerResponse != 0) {
                    Card attackerResponseCard = attacker.getCard(attackerResponse);
                    table.attack(attackerResponseCard);
                    properAttackerResponse = true;
                    attacker.useCard(attackerResponse);
                    announceCardPlayed(attacker, attackerResponseCard);
                    return false;
                } else {
                    System.out.println("\n" + attacker + " has chosen to end the round!");
                    properAttackerResponse = true;
                    table.finish();
                    return true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid attack card!");
                properAttackerResponse = false;
            }
        }
        return true;
    }

}
