package snake_and_ladder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Snake {
    private int head;
    private int tail;

    public Snake(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }
}
