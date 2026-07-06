package parking_lot;

public class ParkingBlock {
    private boolean isFilled;
    private VehicleType blockType;
    private int blockNumber;

    private boolean canAccommodate(Vehicle vehicle){
        if (blockType == VehicleType.Heavy)
            return true;
        else if (blockType == VehicleType.Light && (vehicle.getVehicleType()==VehicleType.Light || vehicle.getVehicleType()==VehicleType.TwoWheeler))
            return true;
        else if (blockType == VehicleType.TwoWheeler && vehicle.getVehicleType() == VehicleType.TwoWheeler)
            return true;

        return false;
    }
}
