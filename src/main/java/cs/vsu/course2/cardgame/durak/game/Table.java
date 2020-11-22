package cs.vsu.course2.cardgame.durak.game;

import cs.vsu.course2.cardgame.durak.card.Card;
import cs.vsu.course2.cardgame.durak.card.Suit;

import java.util.ArrayList;

public class Table {

    private ArrayList<Card> playedCards = new ArrayList<>();
    private boolean completed;

    public Table(){
        completed = false;
    }

    public Table(Card card){
        playedCards.add(card);
        completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggleCompleted() {
        completed = !completed;
    }

    public void attack(Card attackCard) {
        if (canAttack() && isValidAttack(attackCard)) {
            playedCards.add(attackCard);
        } else {
            throw new IllegalArgumentException("You can't attack.");
        }
    }

    public void respond(Card responseCard, Suit trump) {
        if(canDefend() && isValidDefend(responseCard, trump)){
            playedCards.add(responseCard);
        } else {
            throw new IllegalArgumentException("You can't defend.");
        }
        /*
        if (openPair()) {
            Dealer open = currentOpenPair();
            open.response(responseCard);
        }
         */
    }

    public boolean canAttack() {
        return !openPair();
    }

    public boolean canDefend(){
        return openPair();
    }

    public boolean isValidAttack(Card attackCard){
        if (playedCards.isEmpty())
            return true;
        for(Card card : playedCards){
            if(card.getRank() == attackCard.getRank())
                return true;
        }
        return false;
    }

    public boolean isValidDefend(Card defendCard, Suit trump){
        Card attackCard = playedCards.get(playedCards.size() - 1);
        if (defendCard.getSuit() == trump && attackCard.getSuit() != trump)
            return true;
        if (defendCard.getRank().getRankNum() >= attackCard.getRank().getRankNum()
        && defendCard.getSuit() == attackCard.getSuit()){
            return true;
        }
        return false;
    }

    public boolean finish(){
        toggleCompleted();
        playedCards = new ArrayList<>();
        return openPair();
    }

    public boolean openPair(){
        return playedCards.size()%2 == 1;
    }

    public ArrayList<Card> fetchCards(){
        return playedCards;
    }

    @Override
    public String toString(){
        String table = "\t\t\t||| Field |||\n";
        for (Card card : playedCards){
            table += "\t\t" + card + "\n";
        }
        table += "\t\t\t||| Field |||\n";
        return table;
    }


}
