package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Rank;
import cs.vsu.course2.cardgame.durak.card.Suit;
import cs.vsu.course2.cardgame.durak.player.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void initPlayerTest(){
        Player player = new Player("NickName");
        assertEquals(0, player.cardsInHand());

        player.takeCard(new Card(Rank.ACE, Suit.CLUBS));
        assertEquals(1, player.cardsInHand());

        assertEquals(false, player.isAttacker());
        player.switchRole();
        assertEquals(true, player.isAttacker());

        System.out.println(player.cardList());

        assertEquals(new Card(Rank.ACE, Suit.CLUBS), player.getCard(1));
        player.makeDefender();
        assertEquals(false, player.isAttacker());
    }

}
