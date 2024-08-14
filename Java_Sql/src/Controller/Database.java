package Controller;

import Model.leaderScoreTable;
import Model.player;

import java.sql.*;

public class Database {
    // Connection details
	private final String con_path = "jdbc:postgresql://localhost:5432/DBcubeGame";
	private final String con_userName = "postgres"; 
	private final String con_password = "12345678";	
	
    private Connection con = null;

    // Constructor - test connection
    public Database() {
        try {
            this.connect();
            this.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    // Connect to a database
    private void connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection(con_path, con_userName, con_password);
        // Set schema
        Statement stmt = con.createStatement();
        stmt.execute("SET search_path TO public;");
        stmt.close();
    }

    // Insert new score to leaderBoard in DB
    public void insert_newScore(int PID, int score, int level) {
        try {
            this.connect();
            String query = "INSERT INTO leaderBoard (PID, score, LID) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, PID); // player ID
            stmt.setInt(2, score); // score
            stmt.setInt(3, level); // level
            stmt.executeUpdate(); // run insert query

            this.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Get PID from player using first name
    public int getIDByName(String name) throws Exception {
        int PID = 0;
        try {
            this.connect();
            String getNameFromPID = "SELECT PID FROM player WHERE firstName = ?";
            PreparedStatement stmt = con.prepareStatement(getNameFromPID);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PID = rs.getInt("PID");
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PID;
    }

    // Get the leaderboard from database
    public leaderScoreTable getLeadersBoard(int level) {
        leaderScoreTable topPlayers = new leaderScoreTable();

        try {
            this.connect();
            String query = "SELECT * FROM leaderBoard WHERE LID = ? ORDER BY score DESC LIMIT 10";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, level);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int PID = rs.getInt("PID");
                String name = getNameByID(PID);
                int playerScore = rs.getInt("score");
                topPlayers.setNewLeader(new player(name, playerScore));
            }

            this.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return topPlayers;
    }

    // Delete the weakest player from leaderboard if there are more than 10 players at some level
    public void delWeakPlayer(leaderScoreTable topPlayers, int level) {
        if (topPlayers.countPlayers() == 10) {
            try {
                this.connect();
                String query = "SELECT MIN(score) AS minScore FROM leaderboard WHERE LID = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, level);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int minScore = rs.getInt("minScore");

                    String query2 = "DELETE FROM leaderboard WHERE LID = ? AND score = ? LIMIT 1";
                    PreparedStatement stmt2 = con.prepareStatement(query2);
                    stmt2.setInt(1, level);
                    stmt2.setInt(2, minScore);
                    stmt2.executeUpdate();
                }

                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Get player name from db by using their PID
    public String getNameByID(int PID) throws Exception {
        String name = null;
        try {
            this.connect();
            String getNameFromPID = "SELECT firstName FROM player WHERE PID = ?";
            PreparedStatement stmt = con.prepareStatement(getNameFromPID);
            stmt.setInt(1, PID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("firstName");
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    // Add score to score table    
    public void addScore(int score) {
        try {
            this.connect();
            String query = "INSERT INTO playerscore (score, GID) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, score);
            int GID = getLastGID();
            stmt.setInt(2, GID);
            stmt.executeUpdate();

            this.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addPlayer(String firstName) {
        try {
            this.connect();
            String query = "INSERT INTO player (firstName) VALUES (?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.executeUpdate();
            this.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Add new game to games table in db
    public void addGame(int PID, int LID) {
        try {
            this.connect();//level
            String query = "INSERT INTO game (PID, LID) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, PID);
            stmt.setInt(2, LID);
            stmt.executeUpdate();

            this.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Get the last ID game
    public int getLastGID() throws Exception {
        int GID = 0;
        try {
            this.connect();
            String getLastGIDQuery = "SELECT GID FROM game ORDER BY GID DESC LIMIT 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getLastGIDQuery);
            if (rs.next()) {
                GID = rs.getInt("GID");
            }
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GID;
    }

    // Close the connection with database    
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
