package cs.vsu.course2.cardgame.durak.card;

import java.util.Random;

public class Card implements Comparable<Card>{

    private final Suit suit;
    private final Rank rank;
    private final String color;

    // creating random card
    public Card() {
        Random randomNumber = new Random();
        rank = Rank.values()[randomNumber.nextInt(9)];
        suit = Suit.values()[randomNumber.nextInt(4)];
        color = suit.getColor();
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.color = this.suit.getColor();
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    private String getSimplifiedForm() {
        return "[" + getRank().getSimpleForm() + "|" + getSuit().getSimpleForm() + "]";
    }

    @Override
    public String toString() {
        return getSimplifiedForm() + " <" + rank + ", " + suit + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Card card = (Card) o;

        return (this.suit == card.suit && this.rank == card.rank);
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = 1;
        result = prime * result + ((this.rank == null) ? 0 : this.rank.hashCode());
        result = prime * result + ((this.suit == null) ? 0 : this.suit.hashCode());
        return result;
    }

    @Override
    public int compareTo(Card card) {
        return this.getRank().getRankNum() - card.getRank().getRankNum();
    }
}
