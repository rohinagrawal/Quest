package parking_lot;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
