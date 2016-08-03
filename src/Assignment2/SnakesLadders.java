package Assignment2; /**
 * Created by Umar on 02-Apr-16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Launcher Class for JAVAFX Applications
 * Simply takes input for users and intialize the board by calling controller class
 * before staging the scene
 */
public class SnakesLadders extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        List<Integer> choices = new ArrayList<>();
        choices.add(2);
        choices.add(3);
        choices.add(4);

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(2, choices);
        dialog.setTitle("Snakes&Ladders");
        dialog.setHeaderText("How many players will be playing this game?");
        dialog.setContentText("Number of Players:");

// Traditional way to get the response value.
        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()){
            int player=result.get();
            System.out.println("Your choice: " + player);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            GameController gc= loader.<GameController>getController();
            gc.initData(player);
            primaryStage.setTitle("Snakes and Ladders");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else
            System.exit(-1);



    }


}
