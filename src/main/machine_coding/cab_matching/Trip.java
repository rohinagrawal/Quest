package cab_matching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Trip {
    private CabDriver cabDriver;
    private Rider rider;

    private float driverRating;
    private float riderRating;
}
