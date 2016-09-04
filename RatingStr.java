package sample;

/**
 * Created by window on 23.06.2016.
 */
public class RatingStr {
    private int id;
    private int rating;
    private String name;

    public RatingStr(int id, int rating, String name){
        this.id = id;
        this.rating = rating;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public int getRating(){
        return this.rating;
    }
    public String getName(){
        return this.name;
    }
}
