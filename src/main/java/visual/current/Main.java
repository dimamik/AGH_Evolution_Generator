package visual.current;

import config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.simulation.UniverseSimulation;
import logic.statistics.MapStatistics;
import visual.cellsView.CellsWrapper;

import java.util.LinkedList;


public class Main extends Application {


    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Config.initialize();
        UniverseSimulation universeSimulation = new UniverseSimulation();
        universeSimulation.addNewMap();
        LinkedList<MapStatistics> statisticsLinkedList = universeSimulation.getMapStatistics();
        MapStatistics mapStatistics = statisticsLinkedList.pop();

        Label aliveAnimalsCountProperty = new Label();           // текстовая метка
        aliveAnimalsCountProperty.textProperty().bind(Bindings.convert(mapStatistics.aliveAnimalsCountProperty()));


        Label grassCountProperty = new Label();           // текстовая метка
        aliveAnimalsCountProperty.textProperty().bind(Bindings.convert(mapStatistics.grassCountProperty()));

        ViewRectangularMap viewRectangularMap = new ViewRectangularMap(event -> {
            return;
        });
        CellsWrapper cellsWrapper = new CellsWrapper();
        cellsWrapper.addEventListeners(viewRectangularMap.getArrayOfCells());

        universeSimulation.addNewViewObserver(universeSimulation.getMapSimulations().get(0), cellsWrapper);
        VBox vbox = new VBox(viewRectangularMap, aliveAnimalsCountProperty, grassCountProperty);

        Scene scene = new Scene(vbox, 300, 150);
        stage.setScene(scene);
        stage.setTitle("First Application");
        stage.setWidth(500);
        stage.setHeight(500);

        Timeline oneSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event1) -> {
            universeSimulation.newDayInAllSimulations();
        }));
        oneSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        oneSecondsWonder.play();
        stage.show();
    }
}