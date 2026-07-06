package snake_and_ladder;

import java.util.*;

public class SnakeAndLadderService {
    private SnakeLadderBoard snakeLadderBoard;
    private int noOfPlayers;
    private Queue<Player> players;

    public SnakeAndLadderService(){
        this.snakeLadderBoard = new SnakeLadderBoard();
        this.players = new LinkedList<>();
    }

    public void setPlayers(List<Player> players){
        noOfPlayers = players.size();
        Map<String,Integer> gotis= new HashMap<>();
        players.stream().peek(player -> {
            this.players.add(player);
            gotis.put(player.getId(),0);
        });
        snakeLadderBoard.setGotis(gotis);
    }

    public void setSnakes(List<Snake> snakes){
        this.snakeLadderBoard.setSnakes(snakes);
    }

    public void setLadders(List<Ladder> ladders){
        this.snakeLadderBoard.setLadders(ladders);
    }
}


