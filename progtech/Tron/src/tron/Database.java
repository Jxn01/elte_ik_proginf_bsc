package tron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 *
 * @author jxn
 */
class Database {

    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;

    public Database(HighScore newScore) throws SQLException {
        connect();
        if (newScore != null) {
            insertScore(newScore);
        }

    }

    private void connect() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "jxn");
        connectionProps.put("password", "");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/tron";
        connection = DriverManager.getConnection(dbURL, connectionProps);

        String insertQuery = "INSERT INTO HIGHSCORES (name, score, date) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String updateQuery = "UPDATE HIGHSCORES SET score = ?, date = ? WHERE name = ?";
        updateStatement = connection.prepareStatement(updateQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("name");
            int score = results.getInt("score");
            Timestamp ts = results.getTimestamp("date");
            highScores.add(new HighScore(name, score, ts));
        }
        sortHighScores(highScores);
        return highScores;
    }

    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, (HighScore t, HighScore t1) -> t1.score - t.score);
    }

    private void insertScore(HighScore ns) throws SQLException {
        String name = ns.name;
        int score = ns.score;
        Timestamp ts = ns.ts;
        ArrayList<HighScore> scores = getHighScores();
        if (scores.stream().anyMatch(s -> s.name.equals(ns.name))) {
            score = scores.stream().filter(s -> s.name.equals(ns.name)).findFirst().get().score + 1;
            updateStatement.setString(3, name);
            updateStatement.setInt(1, score);
            updateStatement.setTimestamp(2, ts);
            updateStatement.executeUpdate();
        } else {
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.setTimestamp(3, ts);
            insertStatement.executeUpdate();
        }

    }

}
