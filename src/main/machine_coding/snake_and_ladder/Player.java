package snake_and_ladder;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player {
    private String id;
    private String name;

    Player(String name){
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }
}
