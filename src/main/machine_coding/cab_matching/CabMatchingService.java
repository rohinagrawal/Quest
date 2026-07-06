package cab_matching;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CabMatchingService {
    private ArrayList<Rider> riders;
    private ArrayList<CabDriver> cabDrivers;
    private ArrayList<Trip> trips;

    public CabMatchingService() {
        this.riders = new ArrayList<>();
        this.cabDrivers = new ArrayList<>();
        this.trips = new ArrayList<>();
    }

    public void initialize(){
        addRider("Ram");
        addRider("Laxman");
        addRider("Bharat");
        addDriver("Bheem");
        addDriver("Nakul");
        addDriver("Sahadev");
        addTrip("Ram","Bheem",3,5);
        addTrip("Laxman","Nakul",5,2);
        addTrip("Ram","Sahadev",4,2);
        addTrip("Bharat","Bheem",3,5);
        addTrip("Ram","Bheem",3,1);
        addTrip("Laxman","Sahadev",5,3);
        addTrip("Bharat", "Nakul",5,5);
    }

    public void addRider(String name){
        Rider rider = new Rider(name);
        riders.add(rider);
    }

    public void addDriver(String name){
        CabDriver cabDriver = new CabDriver(name);
        cabDrivers.add(cabDriver);
    }

    public void addTrip(String riderName, String driverName, float riderRating, float driverRating){
        Rider rider = riders.stream().filter(rider1 -> rider1.getName().equals(riderName)).findFirst().orElse(null);
        CabDriver cabDriver = cabDrivers.stream().filter(cabDriver1 -> cabDriver1.getName().equals(driverName)).findFirst().orElse(null);
        Trip trip = new Trip(cabDriver,rider, driverRating,riderRating);
        trips.add(trip);

        if(rider!=null)
        updateRiderRating(rider, riderRating);

        if (cabDriver !=null)
        updateDriverRating(cabDriver, driverRating);
    }

    public void trip(String riderName){
        Rider rider = riders.stream().filter(rider1 -> rider1.getName().equals(riderName)).findFirst().orElse(null);
        CabDriver cabDriver = matchDriver(rider);
        System.out.println(cabDriver.getName() + " " + cabDriver.getRating());
    }

    public CabDriver matchDriver(Rider rider){
//        TODO: Review Business Logic
        List<CabDriver> notAllowed = trips.stream()
                .filter(trip -> trip.getRider().equals(rider) && (trip.getDriverRating() <= 1 || trip.getRiderRating() <= 1))
                .map(Trip::getCabDriver).collect(Collectors.toList());
        cabDrivers.sort(new CabDriver.SortByRating());
        return cabDrivers.stream().filter(cabDriver -> !notAllowed.contains(cabDriver)).findFirst().orElse(null);
    }

    public void updateRiderRating(Rider rider,float riderRating){
        float oldRating = rider.getRating();
        int n = rider.getTrips();
        rider.setTrips(n+1);
        float currRating = ((oldRating * n) + riderRating)/(n+1);
        rider.setRating(currRating);
    }

    public void updateDriverRating(CabDriver cabDriver, float driverRating){
        float oldRating = cabDriver.getRating();
        int n = cabDriver.getTrips();
        cabDriver.setTrips(n+1);
        float currRating = ((oldRating * n) + driverRating)/(n+1);
        cabDriver.setRating(currRating);
    }
}
