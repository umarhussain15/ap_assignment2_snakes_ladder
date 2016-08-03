package Assignment2;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXMessageDialog;
import com.sun.deploy.util.SystemUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller Class which will be linked with the fxml of the stage scene
 */


public class GameController {
    // GUI Controls
    public Button rolldice, NextPlayer;
    public Label player1, player2, player3, player4,
            curpos1, curpos2, curpos3, curpos4,
            lastroll1, lastroll2, lastroll3, lastroll4;
    public Label turn1, turn2, turn3, turn4;
    public Label showlabel, CurTurn, CurDice;
    int playernumber, CurPlayer;
    // List of player objects playing
    List<PlayerClass> players;
    // this board which will be initialize
    int[] Board;

    boolean Champion = false;

    public void DoTurn(ActionEvent actionEvent) throws IOException {
        NextTurn();
    }

    public void NextTurn() {
        PlayerClass temp = players.get(CurPlayer);
        int t = rolldice();
        CurDice.setText(t + "");
        // First check for Dice roll and user turn allowed or not
        // then evaluate the Roll
        temp.turns++;
//        System.out.println(t);
        if (t == 6) {

            if (temp.can_turn == 1) {
                temp.lastturnval = t;
                // Extra chance will not be a new turn
                temp.turns--;
                UpdateUI();
                return;
            } else {
                // Player has 6 after a snake so allow him to turn and give him 2nd turn
                temp.can_turn = 1;
                UpdateUI();
                // avoiding 6 to be added which was used to release palyer from
                // snake bite lock
                temp.lastturnval = 0;
                return;
            }
        }
        // User turn was disabled and he has not hit a six on roll
        else if (temp.can_turn == 0) {
            temp.lastturnval = t;
            UpdateUI();
            CurPlayer = NextCurPlayer(CurPlayer);
            return;
        }
        // if user has 6 on last turn then add that to total which will be added
        else if (temp.lastturnval == 6) {
            temp.lastturnval = t;
            t += 6;
//            System.out.println("Dice val after 6"+t);
        }
        // normal value just store it
        else {
            temp.lastturnval = t;
        }

        // Move will be be discarded
        if ((temp.currentpos + t) > 100) {
            UpdateUI();
            CurPlayer = NextCurPlayer(CurPlayer);
            return;
        } else if (Board[temp.currentpos + t] == 100) {
            temp.currentpos = Board[temp.currentpos + t];

            System.out.println(temp.player_id + " Won - Round Used to Win the Game" + temp.turns);
            for (int i = 0; i < players.size(); i++)
                System.out.println("Players Position " + players.get(i).currentpos);
            UpdateUI();
            Champion = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game End");
            alert.setHeaderText(null);
            alert.setContentText((CurPlayer+1)+" Won the Game");
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> System.exit(0));
            return;
        }
        // Normal Move just move ahead
        else if (Board[temp.currentpos + t] == (temp.currentpos + t)) {
            temp.currentpos = Board[temp.currentpos + t];
            UpdateUI();
            CurPlayer = NextCurPlayer(CurPlayer);
        }
        // Ladder - Move the player to position after ladder but
        // allow him to do one more roll
        else if (Board[temp.currentpos + t] > (temp.currentpos + t)) {

            temp.currentpos = Board[temp.currentpos + t];
            // Extra chance will not be a new turn
            temp.turns--;
            UpdateUI();
        }
        // Snake - Move user to position after snake and disable
        // his turns
        else if (Board[temp.currentpos + t] < (temp.currentpos + t)) {
            temp.currentpos = Board[temp.currentpos + t];
            temp.can_turn = 0;
            UpdateUI();
            CurPlayer = NextCurPlayer(CurPlayer);
        }
    }

    public int NextCurPlayer(int c) {
        Paint value = Color.web("#000000");
        int cmp = 0;
        String name = "";
        if (c < (playernumber - 1))
            cmp = c + 1;
        switch (cmp) {
            case 1:

                value = Color.web("#D32F2F");
                name = "Player 2 Trun:";
                break;
            case 2:

                value = Color.web("#303F9F");
                name = "Player 3 Trun:";
                break;
            case 3:

                value = Color.web("#388E3C");
                name = "Player 4 Trun:";
                break;
            case 0:
                value = Color.web("#795548");
                name = "Player 1 Trun:";
                break;
        }
        CurTurn.setTextFill(value);
        CurTurn.setText(name);
//        System.out.println("Current Player "+c+ "  Next Player "+cmp);
        return cmp;
    }

    public void initData(Integer num) {
        System.out.println("Total Players Now: " + num);
        Board = buildBoard(100);
        Champion = false;
        playernumber = num;
        players = new ArrayList<>(num);
        for (int i = 0; i < num; i++)
            players.add(new PlayerClass(i));
        if (num == 3) {
            player3.setVisible(true);
            curpos3.setVisible(true);
            lastroll3.setVisible(true);
            turn3.setVisible(true);
        }
        if (num == 4) {
            player3.setVisible(true);
            curpos3.setVisible(true);
            lastroll3.setVisible(true);
            turn3.setVisible(true);
            player4.setVisible(true);
            curpos4.setVisible(true);
            lastroll4.setVisible(true);
            turn4.setVisible(true);
        }
        CurPlayer = 0;
        CurTurn.setTextFill(Color.web("#795548"));
        CurTurn.setText("Player 1 Turn");

//        rolldice.onMouseClickedProperty();
    }

    private void UpdateUI() {
        switch (CurPlayer) {
            case 0:
                curpos1.setText(players.get(CurPlayer).currentpos + "");
                lastroll1.setText(players.get(CurPlayer).lastturnval + "");
                turn1.setText(players.get(CurPlayer).turns + "");

                break;
            case 1:
                curpos2.setText(players.get(CurPlayer).currentpos + "");
                lastroll2.setText(players.get(CurPlayer).lastturnval + "");
                turn2.setText(players.get(CurPlayer).turns + "");
                break;
            case 2:
                curpos3.setText(players.get(CurPlayer).currentpos + "");
                lastroll3.setText(players.get(CurPlayer).lastturnval + "");
                turn3.setText(players.get(CurPlayer).turns + "");
                break;
            case 3:
                curpos4.setText(players.get(CurPlayer).currentpos + "");
                lastroll4.setText(players.get(CurPlayer).lastturnval + "");
                turn4.setText(players.get(CurPlayer).turns + "");
                break;
        }

    }


    private int[] buildBoard(int i) {
        int[] array = new int[i + 1];
        for (int j = 1; j <= i; j++)
            array[j] = j;
        /**
         * Initialize Ladder jumps
         */
        array[1] = 38;
        array[4] = 14;
        array[9] = 31;
        array[21] = 42;
        array[28] = 84;
        array[51] = 67;
        array[71] = 91;
        array[80] = 100;
        /**
         * Initialize Snake jumps
         */
        array[98] = 79;
        array[95] = 75;
        array[93] = 73;
        array[87] = 24;
        array[64] = 60;
        array[62] = 19;
        array[54] = 34;
        array[17] = 7;

        return array;
    }

    int rolldice() {
        Random random = new Random();
        int num = random.nextInt(6 - 1 + 1) + 1;

        return num;
    }

}
