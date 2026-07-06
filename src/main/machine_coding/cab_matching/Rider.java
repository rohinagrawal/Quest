package cab_matching;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rider {
    private String name;
    private float rating;
    private int trips;

    public Rider(String name){
        this.name = name;
        rating = 5;
        trips=0;
    }
}
