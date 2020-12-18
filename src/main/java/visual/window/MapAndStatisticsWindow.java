package visual.window;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.objects.AbstractPositionedObject;
import logic.objects.ObjectStates;
import logic.objects.animal.Animal;
import logic.statistics.MapStatisticsGetter;
import visual.map.ViewRectangularMap;
import visual.map.cells.cellsView.CellView;
import visual.map.singleAnimalStatisticsView.AnimalStatisticsView;
import visual.universe.UniverseViewModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import static config.Config.TIMER_DURATION;
import static javafx.animation.Animation.Status.RUNNING;

public class MapAndStatisticsWindow {

    private final int mapNumber;
    private final ViewRectangularMap mapField;
    private final UniverseViewModel universeViewModel;
    private final Timeline time;
    private final MapStatisticsGetter mapStatisticsGetter;
    private final VBox animalFollowingHBox;

    private int dayN;
    private HBox animalStatistics;
    private Animal selectedAnimal;
    private boolean showDominant;
    private Text singleObjectStatistics;
    private int diedOnDay;
    private VBox buttons;
    private Animal previouslySelected;

    public MapAndStatisticsWindow(int mapNumber, UniverseViewModel universeViewModel) {
        this.mapNumber = mapNumber;
        this.universeViewModel = universeViewModel;
        this.mapField = universeViewModel.getNthMapView(mapNumber);
        this.mapField.addNewCellListener(this);
        this.mapStatisticsGetter = universeViewModel.getNthMapStatisticsGetter(mapNumber);

        this.animalStatistics = new HBox();
        this.singleObjectStatistics = new Text();
        this.diedOnDay = -1;
        this.animalFollowingHBox = new VBox();
        this.time = setTimer();
        this.time.setCycleCount(Timeline.INDEFINITE);
        this.time.play();
    }

    public Timeline getTime() {
        return time;
    }

    public HBox generateHBox() {
        this.buttons = getMapButtonsVBox();
        return new HBox(5,
                this.buttons,
                universeViewModel.getNthMapView(mapNumber),
                getMapStatisticsVBox()
        );
    }

    public void cellWasClicked(AbstractPositionedObject object) {
        if (object.getState() == ObjectStates.ANIMAL) {
            dayN = (universeViewModel.getUniverseSimulation().getMapSimulations().get(mapNumber - 1).getDayInMapSimulation());
            TextInputDialog dialog = new TextInputDialog(dayN + " by default");
            dialog.setHeaderText("Please enter number of days you want to get statistics from");
            dialog.setContentText("Days number ");
            Optional<String> result = dialog.showAndWait();
            try {
                result.ifPresent(number -> dayN = Integer.parseInt(number));
            } catch (NumberFormatException e) {
                dayN = (universeViewModel.getUniverseSimulation().getMapSimulations().get(mapNumber - 1).getDayInMapSimulation());
            }
            selectedAnimal = (Animal) object;
            time.stop();
            followAnimal(selectedAnimal);
        }

    }

    private void followAnimal(Animal animal) {
        hidePreviousSelection();
        showSelectedAnimal(animal);

        buttons.getChildren().remove(animalStatistics);

        AnimalStatisticsView animalStatisticsView = new AnimalStatisticsView(
                animal,
                (universeViewModel.getUniverseSimulation().getMapSimulations().get(mapNumber - 1).getDayInMapSimulation())
        );
        dayN = (universeViewModel.getUniverseSimulation().getMapSimulations().get(mapNumber - 1).getDayInMapSimulation());

        if (animal.getEnergy() == 0 && diedOnDay == -1) {
            diedOnDay = dayN;
        } else if (animal.getEnergy() > 0) {
            diedOnDay = -1;
        }
        generateSingleAnimalStatistics(animalStatisticsView, animal);
        animalStatistics = new HBox(singleObjectStatistics, animalFollowingHBox);

        buttons.getChildren().add(animalStatistics);
    }

    private void showDominantGenomeAnimals() {
        LinkedList<Animal> listOfAnimals = mapStatisticsGetter.getAnimalsWithDominantGenome(
                universeViewModel.getUniverseSimulation()
                        .getMapSimulations()
                        .get(mapNumber - 1).getRectangularMap().getAllAnimals()
        );
        CellView[][] cellsViewArray = mapField.getArrayOfCells();
        for (Animal animal : listOfAnimals) {
            cellsViewArray[animal.getPosition().getX()][animal.getPosition().getY()]
                    .makeCellDominant(showDominant);
        }
    }

    private Timeline setTimer() {
        return new Timeline(new KeyFrame(Duration.millis(TIMER_DURATION), (ActionEvent event1) -> {
            universeViewModel.getUniverseSimulation().newDayInNthSimulation(mapNumber);
            if (selectedAnimal != null) {
                followAnimal(selectedAnimal);
            }
        }));
    }

    private void hideDominantAnimals() {
        LinkedList<Animal> listOfAnimals = mapStatisticsGetter.getAnimalsWithDominantGenome(
                universeViewModel.getUniverseSimulation().getMapSimulations().get(mapNumber - 1).getRectangularMap().getAllAnimals()
        );
        CellView[][] cellsViewArray = mapField.getArrayOfCells();
        for (Animal animal : listOfAnimals) {
            cellsViewArray[animal.getPosition().getX()][animal.getPosition().getY()].updateCell(animal);
        }
    }

    private void showSelectedAnimal(Animal animal) {
        previouslySelected = selectedAnimal;
        CellView[][] cellsViewArray = mapField.getArrayOfCells();
        cellsViewArray[animal.getPosition().getX()][animal.getPosition().getY()]
                .makeCellSelected();
    }

    private void hidePreviousSelection() {
        if (previouslySelected != null) {
            CellView[][] cellsViewArray = mapField.getArrayOfCells();
            cellsViewArray[previouslySelected.getPosition().getX()][previouslySelected.getPosition().getY()]
                    .updateCell(previouslySelected);
        }

    }

    private VBox getMapStatisticsVBox() {

        Label sumAnimalsAlive = new Label();
        Label sumGrass = new Label();
        Label averageGenomeTypesList = new Label();
        Label averageEnergyForAliveAnimals = new Label();
        Label averageLiveDurationForDeadAnimals = new Label();
        Label averageKidsNumberForAliveAnimals = new Label();
        Label dayInAnimation = new Label();

        int LEFT_PADDING = 20;
        sumAnimalsAlive.setPadding(new Insets(0, 0, 0, LEFT_PADDING));
        sumGrass.setPadding(new Insets(0, 0, 0, LEFT_PADDING));
        averageGenomeTypesList.setPadding(new Insets(0, 0, 0, LEFT_PADDING));
        averageEnergyForAliveAnimals.setPadding(new Insets(0, 0, 0, LEFT_PADDING));
        averageLiveDurationForDeadAnimals.setPadding(new Insets(0, 0, 0, LEFT_PADDING));
        averageKidsNumberForAliveAnimals.setPadding(new Insets(0, 0, 0, LEFT_PADDING));
        dayInAnimation.setPadding(new Insets(0, 0, 0, LEFT_PADDING));

        sumAnimalsAlive.textProperty().bind(Bindings.convert(mapStatisticsGetter.sumAnimalsAliveProperty()));
        dayInAnimation.textProperty().bind(Bindings.convert(mapStatisticsGetter.getMapStatistics().getMapSimulation().getMapStatistics().dayOfAnimationProperty()));
        sumGrass.textProperty().bind(Bindings.convert(mapStatisticsGetter.sumGrassProperty()));
        averageGenomeTypesList.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageGenomeTypesListProperty()));
        averageEnergyForAliveAnimals.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageEnergyForAliveAnimalsProperty()));
        averageLiveDurationForDeadAnimals.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageLiveDurationForDeadAnimalsProperty()));
        averageKidsNumberForAliveAnimals.textProperty().bind(Bindings.convert(mapStatisticsGetter.averageKidsNumberForAliveAnimalsProperty()));

        Text animationDayCapture = new Text("Day of animation: \t");
        Text animalsAlive = new Text("Animals alive: \t");
        Text grassAmountCapture = new Text("Total grass amount: \t");
        Text averageGenomeCapture = new Text("Average genomes in genotype of alive animals: \t");
        Text averageEnergyCapture = new Text("Average energy for alive animals: \t");
        Text averageLiveDuration = new Text("Average live duration for dead animals: \t");
        Text averageKidsNumberForAlive = new Text("Average kids number for alive animals: \t");

        return new VBox(
                animationDayCapture,
                dayInAnimation,
                animalsAlive,
                sumAnimalsAlive,
                grassAmountCapture,
                sumGrass,
                averageGenomeCapture,
                averageGenomeTypesList,
                averageEnergyCapture,
                averageEnergyForAliveAnimals,
                averageLiveDuration,
                averageLiveDurationForDeadAnimals,
                averageKidsNumberForAlive,
                averageKidsNumberForAliveAnimals);
    }

    private VBox getMapButtonsVBox() {
        Button stopAnimation = new Button("Stop Simulation");
        stopAnimation.setOnAction(e -> {
                    if (time.getStatus() == RUNNING) {
                        time.stop();
                        stopAnimation.setText("Start Simulation");
                        showDominantGenomeAnimals();
                    } else {
                        time.play();
                        stopAnimation.setText("Stop Simulation");
                    }
                }

        );

        Button newDay = new Button("New Day");
        newDay.setOnAction(e -> {
                    universeViewModel.getUniverseSimulation().newDayInNthSimulation(mapNumber);
                    if (selectedAnimal != null) {
                        followAnimal(selectedAnimal);
                    }
                    showDominantGenomeAnimals();
                }

        );

        CheckBox showDominantCheck = new CheckBox("Show dominant genome");
        showDominantCheck.setSelected(false);

        showDominantCheck.setOnAction((value) -> {
            if (showDominantCheck.isSelected()) {
                time.stop();
                stopAnimation.setText("Start Simulation");
                showDominant = true;
                showDominantGenomeAnimals();
            } else {
                showDominant = false;
                hideDominantAnimals();
            }
        });

        buttons = new VBox(
                stopAnimation,
                newDay,
                showDominantCheck

        );
        return buttons;
    }

    private void generateSingleAnimalStatistics(AnimalStatisticsView animalStatisticsView, Animal animal) {
        singleObjectStatistics = new Text(45, 45,
                "\n\nStatistics of selected object: \n\n"
                        + "Object type: " + "\n\tANIMAL\n"
                        + "Genome types list:\n"
                        + "\t" + Arrays.toString(animalStatisticsView.getGenome())
                        + "\nAll Kids in " + dayN + " days\n"
                        + "\t" + (animalStatisticsView.getAnimalKidsInNDays(dayN))
                        + "\nAll Ancestors in " + dayN + " days\n"
                        + "\t" + (animalStatisticsView.getAnimalAncestorsInNDays(dayN))
                        + "\nEnergy: \n" + "\t" + animal.getEnergy()
        );

        if (diedOnDay != -1) {
            singleObjectStatistics.setText(singleObjectStatistics.getText() +
                    "\n" + "Died on DAY: " + diedOnDay);
        }
    }
}
