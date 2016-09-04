package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.*;


public class Game implements Initializable {
    private static boolean isActive;
    public static int size = 20;
    public static int i = 0;
    public static String player = "Player1";
    private static boolean isBeFirstStart;
    public static Canvas canvashref;
    public static Slider sliderhref;
    public static Button btnhref;
    public static Label ratinghref;
    public static MyThread thread1;
    public static int speed = 250;
    public static int userId = 0;
    public static int maxScore = 0;

    @FXML
    Slider slider;
    @FXML
    Canvas canvas;
    @FXML
    Button butn;
    @FXML
    Button start;
    @FXML
    Label showText;
    @FXML
    Label rating;
    @FXML
    Pane morewindow;
    @FXML
    private TableView<RatingStr> tableView;

    @FXML
    private TableColumn<RatingStr, Integer> columnNumber;
    @FXML
    private TableColumn<RatingStr, Integer> columnRecord;
    @FXML
    private TableColumn<RatingStr, String> columnName;

    public static int cellWidth = 0;


    @FXML
    public void handleButtonAction() {
        if(isActive){
            stop();
        } else {
            start.setText("Stop");
            start();

        }
    }

    public void newGame() throws SQLException, ClassNotFoundException {
        morewindow.toBack();
        butn.toFront();
        start.toBack();
            if(userId == 0){
            player = JOptionPane.showInputDialog("Enter your Name", "Player1");
            if ((player != null) && (player.length() > 0))
                userId = DataBase.newUser(player, 0);
            else userId = DataBase.newUser("PlayerNonName", 0);
        }
        butn.setVisible(false);
        start.setVisible(true);
        showText.setText("Pause");
        handleButtonAction();

    }

    @FXML
    public void keyPressed(KeyEvent e){
        switch ( e.getCode().toString() ){
            case "A": {UserSnake.addMove("left");};break;
            case "S": {UserSnake.addMove("down");};break;
            case "D": {UserSnake.addMove("right");};break;
            case "W": {UserSnake.addMove("top");};break;
            case "LEFT": break;
            case "RIGTH": break;
            case "SPACE": { if( isActive ) stop(); else if(isBeFirstStart) start(); else try {
                newGame();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            } break;
            default: break;
        }
    }


    private void start()  {

        isActive = true;
        showText.setOpacity(0);
        canvas.setOpacity(1);
      //  start.setVisible(false);
        if(!isBeFirstStart) {
            btnhref = this.butn;
            canvashref = this.canvas;
            sliderhref = this.slider;
            GridShow();
            thread1 = new MyThread("first" + Integer.toString(i));
            thread1.setDaemon(true);
            thread1.start();
        }
        isBeFirstStart = true;
    }

    public  void stop(){
        isActive = false;
        start.setText("Start");
        showText.setOpacity(1);
    }

    public static boolean isActive(){
        return isActive;
    }

    public static void go(UserSnake userSnake, BotSnake botSnake) throws SQLException, ClassNotFoundException {
        ArrayList<Point> snakeCoords = userSnake.move( UserSnake.getThisMove() , size);
        if( userSnake.isCrashSelf() || botSnake.isUserCrashed(userSnake.getPosition()) ) {
            gameEnd();
        }
        else {
            clearPoint(snakeCoords.get(snakeCoords.size() - 1));
            clearPoint(botSnake.getTail());
            showPoint(snakeCoords.get(0), 0);
            showPoint(botSnake.getHead(), 1);
            botSnake.deleteTail();
            UserSnake.clearThisMove(); // Удаляем  движение
        }

    }


    private static int  gameEnd() throws SQLException, ClassNotFoundException {

        isActive = false;
        isBeFirstStart = false;
        btnhref.setVisible(true);
        int score = UserSnake.getScore();
        DataBase.updateMaxRating(userId, score );
        String message = "";
        if( maxScore < score ){
            message = " But You set new record!";
            maxScore = score;
        }
        JOptionPane.showMessageDialog( new JFrame(), "You Lose! Your rating - "+ Integer.toString(score)+" points", "Game Over!" + message ,JOptionPane.ERROR_MESSAGE);
        UserSnake.clearScore();
        thread1.interrupt();
        return 0;
    }

    public void GridShow(){
        GraphicsContext  gc = canvashref.getGraphicsContext2D();
        gc.clearRect(0,0, canvashref.getWidth() , canvashref.getHeight() );
        gc.setFill(Color.WHITE);
        //gc.fillRect(1,1,canvas.getWidth() , canvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);

        cellWidth = (int) (canvashref.getWidth()/size);

        for (int i = 0; i < size; i++){
            canvashref.getGraphicsContext2D().strokeLine(0,i*cellWidth, canvashref.getHeight() , i * cellWidth);
            canvashref.getGraphicsContext2D().strokeLine(i*cellWidth, 0  , i * cellWidth , canvashref.getHeight());
        }

        gc.strokeLine(0, canvashref.getHeight() , canvashref.getWidth() ,  canvashref.getHeight() );
        gc.strokeLine( canvashref.getWidth()  , 0 , canvashref.getWidth() ,  canvashref.getHeight()  );

        gc.stroke();
    }

        public static void showPoint(Point point,int color) {
            GraphicsContext gc = canvashref.getGraphicsContext2D();
            gc.setFill(color > 0?Color.BURLYWOOD:Color.THISTLE);
            gc.fillRect(point.getX() * cellWidth + 1 , point.getY() * cellWidth + 1 , cellWidth - 3 + 1, cellWidth - 3 + 1);
        }

       public static void showPoints(ArrayList<Point> points, int color) {
           GraphicsContext gc = canvashref.getGraphicsContext2D();
           gc.setFill(color > 0?Color.BURLYWOOD:Color.THISTLE);
           for (Point point : points) {
               gc.fillRect(point.getX() * cellWidth + 1 , point.getY() * cellWidth + 1 , cellWidth - 3 + 1, cellWidth - 3 + 1);
           }

       }
        public static void clearPoint( Point point ){
            GraphicsContext gc = canvashref.getGraphicsContext2D();
            gc.clearRect(point.getX() * cellWidth + 1 + 1, point.getY() * cellWidth + 1 + 1 , cellWidth - 3, cellWidth -3);

        }


        public static void showApple(Apple apple){
            GraphicsContext gc = canvashref.getGraphicsContext2D();
            if( apple.getType() == 1 ) gc.setFill(Color.PALEVIOLETRED);
            else gc.setFill(Color.ROYALBLUE);
            gc.fillRect(apple.getX() * cellWidth + 1 , apple.getY() * cellWidth + 1  , cellWidth -3 + 1, cellWidth -3 + 1);

        }

        public static int getSpeed(){
        return speed;
    }


        public void cngSpeed() {
            speed = (int) (1100 / slider.getValue()) ;
        }

        public void cngSpeed1(KeyEvent event) {
           switch (event.getCode().toString()) {
               case "LEFT": speed = (int) (1100 / Math.max(  slider.getMin() , (slider.getValue() - 1) )) ; break;
               case "RIGHT":   speed = (int) (1100 / Math.min(  slider.getMax() , (slider.getValue() - 1) )) ; break;
               default: break;
           }
        }

    public void showWindow(Event event) {
        morewindow.setVisible(true);
        morewindow.toFront();
        if(isActive) stop();
    }

    public void closendow(ActionEvent actionEvent) {
        morewindow.toBack();
        morewindow.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNumber.setCellValueFactory(new PropertyValueFactory<RatingStr, Integer>("id"));
        columnRecord.setCellValueFactory(new PropertyValueFactory<RatingStr, Integer>("rating"));
        columnName.setCellValueFactory(new PropertyValueFactory<RatingStr, String>("name"));
        try {
            tableView.setItems( DataBase.getTableContent() );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableView.setFocusTraversable(false);
        try {
            maxScore = DataBase.getMaxRating();
            rating.setText(Integer.toString( maxScore ));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
