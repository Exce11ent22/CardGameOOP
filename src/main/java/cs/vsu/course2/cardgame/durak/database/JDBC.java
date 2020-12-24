package cs.vsu.course2.cardgame.durak.database;

import cs.vsu.course2.cardgame.durak.game.Game;

import java.sql.*;

public class JDBC {

    public Game addSessionData(Game game) {
        try {
            return addToDB(game);
        } catch (SQLException e) {
            throw new RuntimeException("Can not save game.", e);
        }
    }

    private Game addToDB(Game game) throws SQLException {
        try (Connection c = getConnection()) {
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO sessions_data (winner, loser, rounds, time_start, time_end) VALUES (?, ?, ?, ?, ?);"
            );
            statement.setString(1, game.getWinner().toString());
            statement.setString(2, game.getLoser().toString());
            statement.setInt(3, game.getRound());
            statement.setString(4, game.getTimeStart());
            statement.setString(5, game.getTimeEnd());
            statement.executeUpdate();
        }
        return game;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/card_game?serverTimezone=Europe/Moscow";
        String user = "root";
        String password = "DatabasePassword123456";
        return DriverManager.getConnection(url, user, password);
    }

}
