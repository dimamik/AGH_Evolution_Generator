package visual;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.animal.Animal;
import objects.animal.Gens;
import objects.maps.RectangularMap;
import random.RandomGenerator;

import java.util.Arrays;

import static config.Config.HEIGHT;
import static config.Config.WIDTH;

public class VisualMain extends Application {

    static RectangularMap rectangularMap;
    static Button btn;
    static Text getAverageEnergyForAliveAnimals;
    static Text getAverageKidsNumberForAliveAnimals;
    static Text getAverageLiveDuration;
    static Text getAverageGenomeList;
    static Text getAnimalCount;
    static Text getGrassCount;

    @Override
    public void start(Stage stage) {

        updateMap(stage);

        Timeline oneSecondsWonder = new Timeline(new KeyFrame(Duration.millis(2000), (ActionEvent event1) -> {
//            rectangularMap.newDay();
            rectangularMap.newDay();
            updateMap(stage);
            System.out.println("TIMEOUT IS RUNNING");
        }));
        oneSecondsWonder.setCycleCount(Timeline.INDEFINITE);//Set to run Timeline forever or as long as the app is running.

        //Button used to pause and play the Timeline
        btn.setText("Stop/Start evolution");
        btn.setOnAction((ActionEvent event) -> {
            switch (oneSecondsWonder.getStatus()) {
                case RUNNING -> oneSecondsWonder.pause();
                case PAUSED, STOPPED -> oneSecondsWonder.play();
            }
        });

//        oneSecondsWonder.play();
    }

    public static void main(String[] args) {
        btn = new Button();
        rectangularMap = new RectangularMap();
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH, HEIGHT), 200, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH, HEIGHT), 200, new Gens(genSeq)));
        rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH, HEIGHT), 200, new Gens(genSeq2)));
        rectangularMap.addObject(new Animal(RandomGenerator.getRandomPosition(WIDTH, HEIGHT), 200, new Gens(genSeq2)));
        launch();
    }

    public Parent createMap() {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);
        VisualRectangularMap visualRectangularMap = new VisualRectangularMap(event -> {
            //TODO MOUSE CLICKED ON CELL
            return;
        }, rectangularMap);
        getAverageEnergyForAliveAnimals = new Text("getAverageEnergyForAliveAnimals " + rectangularMap.getStatistics().getAverageEnergyForAliveAnimals());
        getAverageKidsNumberForAliveAnimals = new Text("getAverageKidsNumberForAliveAnimals " + rectangularMap.getStatistics().getAverageKidsNumberForAliveAnimals());
        getAverageLiveDuration = new Text("getAverageLiveDuration " + rectangularMap.getStatistics().getAverageLiveDuration());
        getAverageGenomeList = new Text("getAverageGenomeList " + Arrays.toString(rectangularMap.getStatistics().getAverageGenomeList()));
        getAnimalCount = new Text("getAnimalCount " + rectangularMap.getStatistics().getAnimalCount());
        getGrassCount = new Text("getGrassCount " + rectangularMap.getStatistics().getGrassCount());
        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));
        VBox vBox = new VBox(50, visualRectangularMap,btn,getAverageEnergyForAliveAnimals,
                getAverageKidsNumberForAliveAnimals,getAverageLiveDuration,
                getAverageGenomeList,getAnimalCount,getGrassCount);
        vBox.setAlignment(Pos.CENTER_LEFT);
        root.setCenter(vBox);
        return root;

    }

    public void updateMap(Stage stage) {
        Scene scene = new Scene(createMap());
        stage.setScene(scene);
        stage.show();
    }
}