package cs.vsu.course2.cardgame.durak;

import cs.vsu.course2.cardgame.durak.database.JDBC;
import cs.vsu.course2.cardgame.durak.game.GameManager;
import cs.vsu.course2.cardgame.durak.player.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JDBCTest {

    @Test
    public void testJDBC(){
        GameManager gm = new GameManager();
        Player p1 = new Player("Вася");
        Player p2 = new Player("Петя");
        gm.setOne(p1);
        gm.setTwo(p2);
        gm.setWinner(p1);
        gm.setLoser(p2);

        JDBC db = new JDBC();
        db.addSessionData(gm);

        assertEquals("Вася", db.getWinner());
        assertEquals("Петя", db.getLoser());
        assertEquals(0, db.getRounds());
        assertEquals("game wont start", db.getTimeStart());
        assertEquals("game wont end", db.getTimeEnd());
    }

}
