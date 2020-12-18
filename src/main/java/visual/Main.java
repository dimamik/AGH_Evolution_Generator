package visual;

import config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import visual.universe.UniverseViewModel;
import visual.window.MapAndStatisticsWindow;


public class Main extends Application {
    Timeline timeFirst;
    Timeline timeSecond;
    MapAndStatisticsWindow window1;
    MapAndStatisticsWindow window2;

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Config.initialize();
//
//        UniverseSimulation universeSimulation = new UniverseSimulation();
//        universeSimulation.addNewMap();
//
//        LinkedList<MapStatistics> statisticsLinkedList = universeSimulation.getMapStatistics();
//        MapStatistics mapStatistics = statisticsLinkedList.pop();
//
//        MapStatisticsGetter mapStatisticsGetter = new MapStatisticsGetter(mapStatistics);
//
//
//        Label aliveAnimalsCountProperty = new Label();           // текстовая метка
//        aliveAnimalsCountProperty.textProperty().bind(Bindings.convert(mapStatistics.aliveAnimalsCountProperty()));
//
//
//        Label children = new Label();           // текстовая метка
//        children.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageGenomeTypesListProperty()));
//
//
//        Label grassCountProperty = new Label();           // текстовая метка
//        grassCountProperty.textProperty().bind(Bindings.convert(mapStatistics.genomeTypesSumProperty()));
//
//        ViewRectangularMap viewRectangularMap = new ViewRectangularMap(event -> {
//            return;
//        });
//        CellsWrapper cellsWrapper = new CellsWrapper();
//        cellsWrapper.addEventListeners(viewRectangularMap.getArrayOfCells());
//
//        universeSimulation.addNewViewObserver(universeSimulation.getMapSimulations().get(0), cellsWrapper);
//        Label label = new Label();
//        label.textProperty().bind(Bindings.convert(universeViewModel.getNthMapStatisticsGetter(1).averageGenomeTypesListProperty()));
//        VBox vbox = new VBox(universeViewModel.getNthMapView(1), universeViewModel.getNthMapView(2),
//                label );

//        UniverseViewModel universeViewModel = new UniverseViewModel();
//        universeViewModel.addNewMap();
//
//        Scene scene = new Scene(vbox, 300, 150);
//        stage.setScene(scene);
//        stage.setTitle("First Application");
//        stage.setWidth(500);
//        stage.setHeight(500);
//
//        Timeline oneSecondsWonder = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent event1) -> {
//            universeViewModel.getUniverseSimulation().newDayInNthSimulation(1);
//            universeViewModel.getUniverseSimulation().newDayInNthSimulation(2);
//        }));
//
//        oneSecondsWonder.setCycleCount(Timeline.INDEFINITE);
//        oneSecondsWonder.play();

        UniverseViewModel universeViewModel = new UniverseViewModel();
        CheckBox checkIfTwoMaps = new CheckBox("Show two maps");
        checkIfTwoMaps.setSelected(false);

        VBox mainVBox = new VBox(15, checkIfTwoMaps);
        Scene scene = new Scene(mainVBox, 900, 800);
        stage.setScene(scene);


        timeFirst = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent event1) -> {
            universeViewModel.getUniverseSimulation().newDayInNthSimulation(1);
        }));
        timeFirst.setCycleCount(Timeline.INDEFINITE);
        timeSecond = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent event1) -> {
            universeViewModel.getUniverseSimulation().newDayInNthSimulation(2);
        }));
        timeSecond.setCycleCount(Timeline.INDEFINITE);


        window1 = new MapAndStatisticsWindow(
                1, universeViewModel, timeFirst
        );
        mainVBox.getChildren().add(
                window1.generateHBox()
        );

        checkIfTwoMaps.setOnAction((value) -> {
            if (checkIfTwoMaps.isSelected() && universeViewModel.getSize() == 1) {
                universeViewModel.addNewMap();
                //TODO Append new Child of My MapWithStatistics
                System.out.println("HELLO" + scene);
                window2 = new MapAndStatisticsWindow(
                        2, universeViewModel, timeSecond
                );
                mainVBox.getChildren().add(
                        window2.generateHBox()
                );
            } else if (!checkIfTwoMaps.isSelected() && universeViewModel.getSize() == 2) {
                universeViewModel.removeLastMap();
                System.out.println("HELLO 2");
                timeSecond.stop();
                mainVBox.getChildren().remove(mainVBox.getChildren().size() - 1);
            }
        });

        stage.show();
    }

    public void StopAnimation(ActionEvent actionEvent) {
//         oneSecondsWonder.stop();
    }
}