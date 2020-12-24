package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.dealer.Dealer;
import cs.vsu.course2.cardgame.durak.deck.Deck;
import cs.vsu.course2.cardgame.durak.player.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DealerTest {

    @Test
    public void dealCardsTest(){
        Player p1 = new Player("First");
        Player p2 = new Player("Second");
        Deck deck = new Deck();
        Dealer.dealCards(p1, p2, deck);
        assertEquals(6, p1.cardsInHand());
        assertEquals(6, p2.cardsInHand());
        assertEquals(24, deck.size());
    }

    @Test
    public void replenishTest(){
        Player player = new Player("NickName");
        Deck deck = new Deck();
        Dealer.replenish(player, deck);
        assertEquals(6, player.cardsInHand());

        player.takeCard(deck.draw());
        Dealer.replenish(player, deck);
        assertEquals(7, player.cardsInHand());
    }

}
