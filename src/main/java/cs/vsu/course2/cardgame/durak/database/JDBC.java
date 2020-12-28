package cs.vsu.course2.cardgame.durak.database;

import cs.vsu.course2.cardgame.durak.game.GameManager;

import java.sql.*;

public class JDBC {

    public GameManager addSessionData(GameManager gm) {
        try {
            return addToDB(gm);
        } catch (SQLException e) {
            throw new RuntimeException("Can not save game.", e);
        }
    }

    public String getWinner(){
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT winner FROM sessions_data");
            ResultSet rs = statement.executeQuery();
            while (!rs.isLast()){
                rs.next();
            }
            return rs.getString(1);
        } catch (SQLException e){
            throw new RuntimeException("Can not get winner.", e);
        }
    }

    public String getLoser(){
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT loser FROM sessions_data");
            ResultSet rs = statement.executeQuery();
            while (!rs.isLast()){
                rs.next();
            }
            return rs.getString(1);
        } catch (SQLException e){
            throw new RuntimeException("Can not get loser.", e);
        }
    }

    public int getRounds(){
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT rounds FROM sessions_data");
            ResultSet rs = statement.executeQuery();
            while (!rs.isLast()){
                rs.next();
            }
            return rs.getInt(1);
        } catch (SQLException e){
            throw new RuntimeException("Can not get rounds.", e);
        }
    }

    public String getTimeStart(){
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT time_start FROM sessions_data");
            ResultSet rs = statement.executeQuery();
            while (!rs.isLast()){
                rs.next();
            }
            return rs.getString(1);
        } catch (SQLException e){
            throw new RuntimeException("Can not get time start.", e);
        }
    }

    public String getTimeEnd(){
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("SELECT time_end FROM sessions_data");
            ResultSet rs = statement.executeQuery();
            while (!rs.isLast()){
                rs.next();
            }
            return rs.getString(1);
        } catch (SQLException e){
            throw new RuntimeException("Can not get time end.", e);
        }
    }

    private GameManager addToDB(GameManager gm) throws SQLException {
        try (Connection c = getConnection()) {
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO sessions_data (winner, loser, rounds, time_start, time_end) VALUES (?, ?, ?, ?, ?);"
            );
            statement.setString(1, gm.getWinner().toString());
            statement.setString(2, gm.getLoser().toString());
            statement.setInt(3, gm.getRound());
            statement.setString(4, gm.getTimeStart());
            statement.setString(5, gm.getTimeEnd());
            statement.executeUpdate();
        }
        return gm;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/card_game?serverTimezone=Europe/Moscow";
        String user = "root";
        String password = "DatabasePassword123456";
        return DriverManager.getConnection(url, user, password);
    }

}
