package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Rank;
import cs.vsu.course2.cardgame.durak.card.Suit;
import cs.vsu.course2.cardgame.durak.player.Hand;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {

    @Test
    public void initHandTest(){
        Hand hand = new Hand();
        assertEquals(6, hand.numberToDraw());

        hand.add(new Card(Rank.ACE, Suit.CLUBS));
        assertEquals(5, hand.numberToDraw());
        assertEquals(1, hand.size());

        assertEquals(new Card(Rank.ACE, Suit.CLUBS), hand.useCardByIndex(0));
        assertEquals(0, hand.size());
    }

}
