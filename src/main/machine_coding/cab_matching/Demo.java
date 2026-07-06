package cab_matching;

public class Demo {
    public static void main(String[] args) {
        CabMatchingService cabMatchingService = new CabMatchingService();
        cabMatchingService.initialize();
        cabMatchingService.trip("Ram");
        cabMatchingService.trip("Laxman");
    }
}
