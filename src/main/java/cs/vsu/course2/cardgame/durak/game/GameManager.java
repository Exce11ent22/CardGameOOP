package cs.vsu.course2.cardgame.durak.game;

import cs.vsu.course2.cardgame.durak.Dealer;
import cs.vsu.course2.cardgame.durak.Deck;
import cs.vsu.course2.cardgame.durak.Table;
import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Suit;
import cs.vsu.course2.cardgame.durak.database.JDBC;
import cs.vsu.course2.cardgame.durak.player.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class GameManager {

    private static Suit trump;
    private Player one, two, attacker, defender, winner, loser;
    private Deck deck;
    private int round;
    private Table currentField;
    private boolean roundInitiated;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String timeStart = "game wont start", timeEnd = "game wont end";
    private final JDBC DB = new JDBC();
    private GameUI gameUI = new GameUI(this);

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

    public Player getAttacker() { return attacker; }

    public Player getDefender() { return defender; }

    public Table getCurrentField() { return currentField; }

    public Deck getDeck() { return deck; }

    public static Suit getTrump() { return trump; }

    public void setOne(Player one) { this.one = one; }

    public void setTwo(Player two) { this.two = two; }

    public void setWinner(Player winner) { this.winner = winner; }

    public void setLoser(Player loser) { this.loser = loser; }

    public void setup() {
        one = gameUI.setupPlayer();
        two = gameUI.setupPlayer();
        deck = new Deck();
        Dealer.dealCards(one, two, deck);
        trump = deck.determTrump();
        setAttacker();
        round = 1;
    }

    protected void game() {
        timeStart = timeFormat.format(Calendar.getInstance().getTime());

        boolean gameOver = false;
        while (!gameOver) {
            gameUI.RoundData();
            boolean switchCondition = round();

            if (win()) {
                gameOver = true;
            } else {
                attacker.replenish(deck);
                defender.replenish(deck);
                round++;
                if (switchCondition)
                    switchRoles();
            }
        }
        gameUI.gameEnded();

        timeEnd = timeFormat.format(Calendar.getInstance().getTime());
        DB.addSessionData(this);
    }

    private boolean round() {
        roundInitiated = false;

        int attack = gameUI.playerInput(attacker, roundInitiated);
        Card attackerCard = attacker.useCard(attack);
        gameUI.announceCardPlayed(attacker, attackerCard);

        if (win()) {
            return true;
        }

        Table roundField = new Table(attackerCard);
        currentField = roundField;
        roundInitiated = true;

        while (!roundField.isCompleted()) {
            boolean defenderTurn = defenderResponse(roundField);
            if (defenderTurn || win()) {
                roundInitiated = false;
                currentField = null;
                return false;
            }
            boolean attackerTurn =  attackerResponse(roundField);
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

    private void setAttacker(){
        ArrayList<Card> cardsOfP1 = this.one.getHand().getCards();
        ArrayList<Card> cardsOfP2 = this.two.getHand().getCards();
        ArrayList<Card> trumpsOfP1 = new ArrayList<>();
        ArrayList<Card> trumpsOfP2 = new ArrayList<>();

        for (Card card : cardsOfP1)
            if (card.getSuit() == trump) trumpsOfP1.add(card);

        for (Card card : cardsOfP2)
            if (card.getSuit() == trump) trumpsOfP2.add(card);

        if (trumpsOfP1.size() != 0 && trumpsOfP2.size() == 0){
            attackerDefender(one, two);
        } else if (trumpsOfP1.size() == 0 && trumpsOfP2.size() != 0){
            attackerDefender(two, one);
        } else if (trumpsOfP1.size() == 0){
            attackerDefender(one, two);
        } else {
            Collections.sort(trumpsOfP1);
            Collections.sort(trumpsOfP2);
            if (trumpsOfP1.get(0).getRank().getRankNum() < trumpsOfP2.get(0).getRank().getRankNum()){
                attackerDefender(one, two);
            } else {
                attackerDefender(two, one);
            }
        }
    }

    private void attackerDefender(Player p1, Player p2){
        attacker = p1;
        p1.makeAttacker();
        defender = p2;
        p2.makeDefender();
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
                defenderResponse = gameUI.playerInput(defender, roundInitiated);
                if (defenderResponse != 0) {
                    Card defenderResponseCard = defender.getCard(defenderResponse);
                    table.respond(defenderResponseCard, trump);
                    properDefenderResponse = true;
                    defender.useCard(defenderResponse);
                    gameUI.announceCardPlayed(defender, defenderResponseCard);
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
                attackerResponse = gameUI.playerInput(attacker, roundInitiated);
                if (attackerResponse != 0) {
                    Card attackerResponseCard = attacker.getCard(attackerResponse);
                    table.attack(attackerResponseCard);
                    properAttackerResponse = true;
                    attacker.useCard(attackerResponse);
                    gameUI.announceCardPlayed(attacker, attackerResponseCard);
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
