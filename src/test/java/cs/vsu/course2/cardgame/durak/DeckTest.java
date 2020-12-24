package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Suit;
import cs.vsu.course2.cardgame.durak.deck.Deck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void drawTest(){
        Deck deck = new Deck();
        Card card = new Card();
        assertEquals(card.getClass(), deck.draw().getClass());

        int size = deck.size();
        for (int i = 0; i < size; i++){
            deck.draw();
        }

        assertEquals(null, deck.draw());
    }

    @Test
    public void determTrumpTest(){
        Deck deck = new Deck();
        assertEquals(Suit.CLUBS.getClass(), deck.determTrump().getClass());
    }

    @Test
    public void initDeckTest(){
        Deck deck = new Deck();
        assertEquals(36, deck.size());
    }

    @Test
    public void isEmptyTest(){
        Deck deck = new Deck();
        assertEquals(false, deck.isEmpty());
        int size = deck.size();
        for (int i = 0; i < size; i++){
            deck.draw();
        }
        assertEquals(true, deck.isEmpty());
    }

}
