package cs.vsu.course2.cardgame.durak.card;

public enum Rank {

    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8,"8"),
    NINE(9,"9"),
    TEN(10,"10"),
    JACK(11,"J"),
    QUEEN(12,"Q"),
    KING(13,"K"),
    ACE(14,"A");

    private int rank;
    private String simpleForm;

    Rank(int i, String simpleForm) {
        this.rank = i;
        this.simpleForm = simpleForm;
    }

    public int getRankNum() {
        return rank;
    }

    public String getSimpleForm(){ return simpleForm; }

}
