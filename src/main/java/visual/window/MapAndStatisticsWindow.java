package visual.window;

import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.objects.animal.Animal;
import logic.statistics.MapStatisticsGetter;
import visual.map.ViewRectangularMap;
import visual.map.singleAnimalStatisticsView.AnimalStatisticsView;
import visual.universe.UniverseViewModel;

import java.util.Arrays;

import static javafx.animation.Animation.Status.RUNNING;

public class MapAndStatisticsWindow {
    int mapNumber;
    ViewRectangularMap mapField;
    UniverseViewModel universeViewModel;
    Timeline time;
    MapStatisticsGetter mapStatisticsGetter;
    VBox animalStatistics;
    VBox statistics;

    public MapAndStatisticsWindow(int mapNumber, UniverseViewModel universeViewModel, Timeline time) {
        this.mapNumber = mapNumber;
        this.universeViewModel = universeViewModel;
        this.mapField = universeViewModel.getNthMapView(mapNumber);
        this.time = time;
        this.mapStatisticsGetter = universeViewModel.getNthMapStatisticsGetter(mapNumber);
        this.animalStatistics = new VBox();
        mapField.addNewCellListener(this);
        time.play();
    }

    public HBox generateHBox() {
        Label sumAnimalsAlive = new Label();
        Label sumGrass = new Label();
        Label averageGenomeTypesList = new Label();
        Label averageEnergyForAliveAnimals = new Label();
        Label averageLiveDurationForDeadAnimals = new Label();
        Label averageKidsNumberForAliveAnimals = new Label();
        sumAnimalsAlive.textProperty().bind(Bindings.convert(mapStatisticsGetter.sumAnimalsAliveProperty()));
        sumGrass.textProperty().bind(Bindings.convert(mapStatisticsGetter.sumGrassProperty()));
        averageGenomeTypesList.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageGenomeTypesListProperty()));
        averageEnergyForAliveAnimals.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageEnergyForAliveAnimalsProperty()));
        averageLiveDurationForDeadAnimals.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageLiveDurationForDeadAnimalsProperty()));
        averageKidsNumberForAliveAnimals.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageKidsNumberForAliveAnimalsProperty()));

        Button stopAnimation = new Button("Stop Simulation");
        stopAnimation.setOnAction(e -> {
                    if (time.getStatus() == RUNNING) {
                        time.stop();
                        stopAnimation.setText("Start Simulation");
                    } else {
                        time.play();
                        stopAnimation.setText("Stop Simulation");
                    }
                }

        );

        Button newDay = new Button("New Day");
        newDay.setOnAction(e -> universeViewModel.getUniverseSimulation().newDayInNthSimulation(mapNumber)

        );

        VBox buttons = new VBox(
                stopAnimation,
                newDay

        );


        statistics = new VBox(sumAnimalsAlive,
                sumGrass,
                averageGenomeTypesList,
                averageEnergyForAliveAnimals,
                averageLiveDurationForDeadAnimals,
                averageKidsNumberForAliveAnimals)
        ;

        HBox hBox = new HBox(5,
                buttons,
                universeViewModel.getNthMapView(mapNumber),
                statistics
        );
        return hBox;
    }

    public void cellWasClicked(AbstractPositionedObject object) {
        if (object.getState() == ObjectStates.ANIMAL) {
            Animal animal = (Animal) object;
            statistics.getChildren().remove(animalStatistics);
            AnimalStatisticsView animalStatisticsView = new AnimalStatisticsView(
                    (Animal) object,
                    (universeViewModel.getUniverseSimulation().getMapSimulations().get(mapNumber - 1).getDayInMapSimulation())
            );
            Label genome = new Label(Arrays.toString(animalStatisticsView.getGenome()));
            Label getAnimalKidsInNDays = new Label(Double.toString(animalStatisticsView.getAnimalKidsInNDays(5)));
            Label getAnimalAncestorsInNDays = new Label(Double.toString(animalStatisticsView.getAnimalAncestorsInNDays(5)));
            Label animalEnergy = new Label(Integer.toString(animal.getEnergy()));
            animalStatistics = new VBox(genome, getAnimalKidsInNDays, getAnimalAncestorsInNDays, animalEnergy);

            statistics.getChildren().add(animalStatistics);
            //TODO Change State of Animal to followed or something like that to follow days of life
        }

    }
}
