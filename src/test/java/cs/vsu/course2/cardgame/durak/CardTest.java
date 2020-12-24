package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Rank;
import cs.vsu.course2.cardgame.durak.card.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void testCardGetters(){
        Card card = new Card(Rank.ACE, Suit.CLUBS);
        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.CLUBS, card.getSuit());
        assertEquals("Black", card.getColor());
    }

}
