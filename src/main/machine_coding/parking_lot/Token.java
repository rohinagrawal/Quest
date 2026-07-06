package parking_lot;

import java.sql.Timestamp;
import java.util.UUID;

public class Token {
    private UUID id;
    private String parkingBuildingId;
    private Vehicle vehicle;
    private User user;
    private Timestamp entryTime;
    private int floorNo;
    private int blockNo;
}
