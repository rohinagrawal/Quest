package snake_and_ladder;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SnakeLadderBoard {

    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<String,Integer> gotis;

    public SnakeLadderBoard() {
        this.size = 100;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.gotis = new HashMap<>();
    }
}
