package cs.vsu.course2.cardgame.durak.card;

public enum Suit {

    HEARTS("♥"),
    DIAMONDS("♦"),
    SPADES("♠"),
    CLUBS("♣");

    private int suit;
    private String simpleForm;


    Suit(String simpleForm) {
        this.simpleForm = simpleForm;
    }

    public String getSimpleForm(){ return simpleForm; }

    public String getColor() {
        if (this == HEARTS || this == DIAMONDS) {
            return "Red";
        } else {
            return "Black";
        }
    }

}
