package sample;

/**
 * Created by window on 22.06.2016.
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;

public  class DataBase {

    private static Connection connection;
    private static PreparedStatement stmp = null;

    private static void connect() throws ClassNotFoundException, SQLException {
        JOptionPane.showMessageDialog( new JFrame(), Main.dbpath );
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:E:/Learns/snake_first/src/sample/results");

    }

    private static void close() throws SQLException {
        stmp.close();
        connection.close();
    }

    public static int newUser(String name,int rating) throws SQLException, ClassNotFoundException {

        connect();
        stmp = connection.prepareStatement("INSERT INTO main ('name', 'rating') VALUES (?, ?)");
        stmp.setString((int) 1, name);
        stmp.setInt((int) 2, rating);
        stmp.executeUpdate();
        int last_id = stmp.getGeneratedKeys().getInt(1);
        close();
        return last_id;

    }

    public static void updateMaxRating(int userid, int rating) throws SQLException, ClassNotFoundException {
        connect();
        stmp = connection.prepareStatement("SELECT rating FROM main WHERE id = ?");
        stmp.setInt((int) 1, userid);
        ResultSet res = stmp.executeQuery();
        int max = res.getInt("rating");
        if( max < rating){
            stmp = connection.prepareStatement("UPDATE main SET rating = ? WHERE id = ?");
            stmp.setInt((int) 1, rating);
            stmp.setInt((int) 1+1, userid);
            stmp.executeUpdate();
        }
        close();
    }

    public static int getMaxRating() throws SQLException, ClassNotFoundException {
        connect();
        stmp = connection.prepareStatement("SELECT MAX(rating) FROM main WHERE 1");
        ResultSet res = stmp.executeQuery();
        int maxRating = res.getInt(1);
        close();
        return maxRating;
    }

    public static ObservableList<RatingStr> getTableContent() throws SQLException, ClassNotFoundException {
        ObservableList<RatingStr> tableContent = FXCollections.observableArrayList();
        connect();
        stmp = connection.prepareStatement("SELECT * FROM main WHERE 1 ORDER BY rating DESC LIMIT 0,10");
        ResultSet res = stmp.executeQuery();
        int i = 1;

        while (res.next()){
            tableContent.add(new RatingStr(i, res.getInt("rating"), res.getString("name") ));
            i++;
        }

        return tableContent;
    }

}
