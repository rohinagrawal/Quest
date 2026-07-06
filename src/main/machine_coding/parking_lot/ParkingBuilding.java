package parking_lot;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParkingBuilding {
    private String id;
    private List<Floor> floors;
    private String Address;
}
