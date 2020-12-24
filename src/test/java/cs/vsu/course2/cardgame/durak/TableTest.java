package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Rank;
import cs.vsu.course2.cardgame.durak.card.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TableTest {

    @Test
    public void initTableTest(){
        Table table = new Table();
        assertEquals(false, table.isCompleted());
        table.finish();
        assertEquals(true, table.isCompleted());
    }

    @Test
    public void attackAndDefendTest(){
        Table table = new Table();
        Suit trump = Suit.HEARTS;
        table.attack(new Card(Rank.SIX, Suit.CLUBS));
        table.respond(new Card(Rank.ACE, Suit.CLUBS), trump);
        table.attack(new Card(Rank.ACE, Suit.DIAMONDS));
        table.respond(new Card(Rank.SIX, trump), trump);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SIX, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.SIX, trump));
        assertEquals(cards, table.fetchCards());
    }

}
