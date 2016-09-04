package sample;

/**
 * Created by window on 18.06.2016.
 */
import javax.swing.*;
import java.awt.*;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

class MyThread extends Thread implements Runnable {


    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        UserSnake userSnake = new UserSnake();
        Apple apple = new Apple(Game.size, userSnake);
        BotSnake botSnake = new BotSnake();
        Point sapple = (Point) apple;
        Game.showPoints(userSnake.getPosition(),0);
        Game.showApple(apple);
        Game.showPoints(botSnake.getPosition(), 1);
        botSnake.go(apple);
        try {
            Game.go(userSnake, botSnake);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while( !this.isInterrupted()  ) {
            if (Game.isActive()) {

                if ( userSnake.isEatApple(sapple) ) {
                    Game.showPoint(sapple, 0);
                    apple = new Apple(Game.size , userSnake);
                    sapple = (Point) apple;
                    Game.showApple(apple);
                    userSnake.incScore(apple.getType());
                } else {
                   userSnake.deleteTail();
                }// Удаляем хвост

                botSnake.go(apple);
                if (botSnake.isEatApple(sapple) ) {
                    Game.showPoint(sapple, 1);
                    apple = new Apple(Game.size , userSnake);
                    sapple = (Point) apple;
                    Game.showApple(apple);
                }


                try {
                    Game.go(userSnake, botSnake);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                try {
                    if( !this.isInterrupted()) this.sleep(Game.getSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}