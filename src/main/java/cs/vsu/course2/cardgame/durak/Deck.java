package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Rank;
import cs.vsu.course2.cardgame.durak.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Deck {

    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        ArrayList<Card> initCards = new ArrayList<>();
        for (int i = 0; i < Suit.values().length; i++) {
            for (int j = 0; j < Rank.values().length; j++) {
                initCards.add(new Card(Rank.values()[j], Suit.values()[i]));
            }
        }
        Collections.shuffle(initCards);
        initCards.forEach((card) -> cards.push(card));
    }

    public Card draw() {
        if (!isEmpty())
            return cards.pop();
        else
            return null;
    }

    public boolean isEmpty() {
        return cards.empty();
    }

    public int size() {
        return cards.size();
    }

    public Suit determTrump() {
        Random r = new Random();
        return cards.get(r.nextInt(cards.size())).getSuit();
    }

}
