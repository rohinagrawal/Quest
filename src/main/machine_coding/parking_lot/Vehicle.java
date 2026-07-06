package parking_lot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {
    private String color;
    private String number;
    private String Company;
    private String model;
    private VehicleType vehicleType;
}
