package parking_lot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {
    private int floorLevel;
    private Map<VehicleType,List<ParkingBlock>> parkingBlockList;

    public Floor(int floorLevel) {
        this.floorLevel = floorLevel;
        parkingBlockList = new HashMap<>();
    }

    public void setLightVehicleParkingBlocks(List<ParkingBlock> parkingBlockList){
        this.parkingBlockList.put(VehicleType.Light,parkingBlockList);
    }

    public void setHeavyVehicleParkingBlocks(List<ParkingBlock> parkingBlockList){
        this.parkingBlockList.put(VehicleType.Heavy,parkingBlockList);
    }

    public void setTwoWheelerVehicleParkingBlocks(List<ParkingBlock> parkingBlockList){
        this.parkingBlockList.put(VehicleType.TwoWheeler,parkingBlockList);
    }
}
