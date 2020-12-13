package visual;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.simulation.UniverseSimulation;

public class VisualMain extends Application {

    static UniverseSimulation universeSimulation;
    static Button btn;
    static Text getAverageEnergyForAliveAnimals;
    static Text getAverageKidsNumberForAliveAnimals;
    static Text getAverageLiveDuration;
    static Text getAverageGenomeList;
    static Text getAnimalCount;
    static Text getGrassCount;

    public static void main(String[] args) {
        btn = new Button();

        universeSimulation = new UniverseSimulation();
        universeSimulation.addNewMap();
        launch();
    }

    @Override
    public void start(Stage stage) {

        updateMap(stage);

        Timeline oneSecondsWonder = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent event1) -> {
//            rectangularMap.newDay();
//            rectangularMap.newDay();
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

    public void createMap() {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);
//        VisualRectangularMap visualRectangularMap = new VisualRectangularMap(event -> {
//            //TODO MOUSE CLICKED ON CELL
//            return;
//        }, rectangularMap);
//        getAverageEnergyForAliveAnimals = new Text("getAverageEnergyForAliveAnimals " + rectangularMap.getStatistics().getAverageEnergyForAliveAnimals());
//        getAverageKidsNumberForAliveAnimals = new Text("getAverageKidsNumberForAliveAnimals " + rectangularMap.getStatistics().getAverageKidsNumberForAliveAnimals());
//        getAverageLiveDuration = new Text("getAverageLiveDuration " + rectangularMap.getStatistics().getAverageLiveDuration());
//        getAverageGenomeList = new Text("getAverageGenomeList " + Arrays.toString(rectangularMap.getStatistics().getAverageGenomeList()));
//        getAnimalCount = new Text("getAnimalCount " + rectangularMap.getStatistics().getAnimalCount());
//        getGrassCount = new Text("getGrassCount " + rectangularMap.getStatistics().getGrassCount());
//        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));
//        VBox vBox = new VBox(50, visualRectangularMap, btn, getAverageEnergyForAliveAnimals,
//                getAverageKidsNumberForAliveAnimals, getAverageLiveDuration,
//                getAverageGenomeList, getAnimalCount, getGrassCount);
//        vBox.setAlignment(Pos.CENTER_LEFT);
//        root.setCenter(vBox);
//        return root;

    }

    public void updateMap(Stage stage) {
//        Scene scene = new Scene(createMap());
//        stage.setScene(scene);
//        stage.show();
    }
}