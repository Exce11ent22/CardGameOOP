package cs.vsu.course2.cardgame.durak.player;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.Dealer;
import cs.vsu.course2.cardgame.durak.Deck;

import java.util.ArrayList;

public class Player {

    private String name;
    private Hand hand;
    private boolean attacker;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        attacker = false;
    }

    public Hand getHand() {
        return hand;
    }

    public void takeCard(Card card) {
        hand.add(card);
    }

    public void replenish(Deck deck) {
        Dealer.replenish(this, deck);
    }

    public int cardsInHand() {
        return hand.size();
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void makeAttacker() {
        attacker = true;
    }

    public void makeDefender() {
        attacker = false;
    }

    public void switchRole() {
        attacker = !attacker;
    }

    @Override
    public String toString() {
        return name;
    }

    public Card getCard(int num) {
        return hand.getCardByIndex(num - 1);
    }

    public Card useCard(int num) {
        return hand.useCardByIndex(num - 1);
    }

    public String cardList() {
        String ret = "\n=== Your Hand ===\n";
        ArrayList<Card> cards = hand.getCards();
        int i = 1;
        for (Card card : cards) {
            ret += i + ") " + card + "\n";
            i += 1;
        }
        return ret;
    }

}
