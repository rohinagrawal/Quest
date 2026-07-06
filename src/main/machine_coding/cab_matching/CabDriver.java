package cab_matching;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
public class CabDriver {
    private String name;
    private float rating;
    private int trips;

    public CabDriver(String name){
        this.name = name;
        rating=5;
        trips=0;
    }

    public static class SortByRating implements Comparator<CabDriver> {
        @Override
        public int compare(CabDriver a, CabDriver b) {
            return (int) (b.getRating()-a.getRating());
        }
    }
}
