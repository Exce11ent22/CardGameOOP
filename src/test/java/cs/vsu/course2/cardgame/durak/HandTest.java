package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.player.Hand;
import cs.vsu.course2.cardgame.durak.player.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {

    @Test
    public void testNumToDraw(){
        Player player = new Player("Vanya");
        Hand hand = player.getHand();
        assertEquals(6, hand.numberToDraw());

        hand.add(new Card());
        assertEquals(5, hand.numberToDraw());

        hand.add(new Card());
        hand.add(new Card());
        hand.add(new Card());
        hand.add(new Card());
        hand.add(new Card());
        hand.add(new Card());
        hand.add(new Card());
        hand.add(new Card());
        assertEquals(0, hand.numberToDraw());
    }

}
