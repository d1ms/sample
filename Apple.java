package sample;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by window on 19.06.2016.
 */
public class Apple extends Point {

    private int type;

    public Apple(int x, int y, int type) {
        super(x, y);
        this.type = type;
    }

    protected  Apple(int size, UserSnake userSnake){
        super(size - 1, size - 1);
        this.type = (int) Math.round(Math.random());
        int nx,ny;
        do {
            nx = (int) (Math.random() * size);
            ny = (int) (Math.random() * size);
        } while ( userSnake.isHavePoint(new Point(nx,ny)) );
        this.setX(nx);
        this.setY(ny);
    }

    public int getType(){
        return type;
    }

}
