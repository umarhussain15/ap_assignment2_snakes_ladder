package Assignment2;

/**
 * Created by Umar on 02-Apr-16.
 */
public class PlayerClass {
    /***
     * currentpos - store current position
     * player_id - to identify player
     * turns- count the turn made by player
     * lastturnval - gives the value of last turn if can_turn is true
     *
     *
     * can_turn 0- Player need 6 to make his turn, 1- Player can turn
     *
     *
     */
    int currentpos;
    int player_id;
    int turns;
    int lastturnval;

    int can_turn;

    /**
     * @param player_id is the id given on player initialization
     */
    public PlayerClass(int player_id) {
        this.player_id = player_id;
        currentpos = 0;
        turns = 0;
        can_turn = 1;
        lastturnval = 0;
    }
}
