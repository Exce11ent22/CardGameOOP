package cs.vsu.course2.cardgame.durak.card;

public enum Rank {

    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);
    private int rank;


    Rank(int i) {
        this.rank = i;
    }

    public int getRankNum() {
        return rank;
    }

}
