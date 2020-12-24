package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.database.JDBC;
import cs.vsu.course2.cardgame.durak.game.Game;
import cs.vsu.course2.cardgame.durak.player.Player;
import org.junit.Test;

public class JDBCTest {

    @Test
    public void connectionTest(){
        Game game = new Game();
        game.setWinner(new Player("Mark"));
        game.setLoser(new Player("Tom"));
        game.setRound(17);
        game.setTimeStart();
        game.setTimeEnd();
        JDBC db = new JDBC();
        db.addSessionData(game);
    }

}
