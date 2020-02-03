package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private int size = 4;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NumberPane numberPane = new NumberPane(size);

        numberPane.setOnMouseMoved(numberPane::mouseMove);
        numberPane.setOnMouseClicked(numberPane::click);

        primaryStage.setScene(new Scene(numberPane, 500, 500));
        primaryStage.setTitle("15 puzzle");
        primaryStage.show();
    }
}
