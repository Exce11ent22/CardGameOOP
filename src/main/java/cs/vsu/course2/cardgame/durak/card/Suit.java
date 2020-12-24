package cs.vsu.course2.cardgame.durak.card;

public enum Suit {

    HEARTS(0),
    DIAMONDS(1),
    SPADES(2),
    CLUBS(3);

    private int suit;


    Suit(int i) {
        this.suit = i;
    }

    public int getSuitNum() {
        return suit;
    }

    public String getColor() {
        if (this == HEARTS || this == DIAMONDS) {
            return "Red";
        } else {
            return "Black";
        }
    }

}
