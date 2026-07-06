package snake_and_ladder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ladder {
    private int start;
    private int end;

    public Ladder(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
