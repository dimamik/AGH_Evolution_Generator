package visual;

import config.Config;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import visual.universe.UniverseViewModel;
import visual.window.MapAndStatisticsWindow;

public class Main extends Application {
    MapAndStatisticsWindow window1;
    MapAndStatisticsWindow window2;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Config.initialize();
        UniverseViewModel universeViewModel = new UniverseViewModel();
        CheckBox checkIfTwoMaps = new CheckBox("Show two maps");
        checkIfTwoMaps.setSelected(false);

        VBox mainVBox = new VBox(15, checkIfTwoMaps);
        Scene scene = new Scene(mainVBox, 900, 800);
        stage.setScene(scene);

        window1 = new MapAndStatisticsWindow(
                1, universeViewModel
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
                        2, universeViewModel
                );
                mainVBox.getChildren().add(
                        window2.generateHBox()
                );
            } else if (!checkIfTwoMaps.isSelected() && universeViewModel.getSize() == 2) {
                universeViewModel.removeLastMap();
                System.out.println("HELLO 2");
                window2.getTime().stop();
                mainVBox.getChildren().remove(mainVBox.getChildren().size() - 1);
            }
        });

        stage.show();
    }

}