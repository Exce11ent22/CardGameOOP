package cs.vsu.course2.cardgame.durak.player;

import cs.vsu.course2.cardgame.durak.card.Card;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

    public boolean toDraw() {
        return size() > 6;
    }

    public int numberToDraw() {
        if (toDraw()) {
            return 0;
        } else {
            return 6 - size();
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCardByIndex(int i) {
        return cards.get(i);
    }

    public Card useCardByIndex(int i) {
        return cards.remove(i);
    }

    @Override
    public String toString() {
        String cardsInHand = "";
        for (Card card : cards) {
            cardsInHand += card + "\n";
        }
        return cardsInHand;
    }

}
