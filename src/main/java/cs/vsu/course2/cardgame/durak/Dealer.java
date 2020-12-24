package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.Deck;
import cs.vsu.course2.cardgame.durak.player.Player;

public class Dealer {

    public static void dealCards(Player one, Player two, Deck deck) {
        int limit = one.getHand().numberToDraw();
        for (int i = 0; i < limit; i++) {
            one.takeCard(deck.draw());
            two.takeCard(deck.draw());
        }
    }

    public static void replenish(Player p, Deck deck) {
        int limit = p.getHand().numberToDraw();
        for (int i = 0; i < limit; i++) {
            if (deck.isEmpty())
                break;
            p.takeCard(deck.draw());
        }
    }

}
