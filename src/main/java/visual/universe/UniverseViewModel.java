package visual.universe;

import logic.simulation.UniverseSimulation;
import logic.statistics.MapStatistics;
import logic.statistics.MapStatisticsGetter;
import visual.map.ViewRectangularMap;
import visual.map.cells.cellsViewModel.CellsWrapper;

import java.util.LinkedList;

public class UniverseViewModel {
    UniverseSimulation universeSimulation;
    LinkedList<MapStatistics> listOfStatistics;
    LinkedList<ViewRectangularMap> listOfMapViews;

    public UniverseViewModel() {
        universeSimulation = new UniverseSimulation();
        listOfStatistics = new LinkedList<>();
        listOfMapViews = new LinkedList<>();
        addNewMap();
    }

    public UniverseSimulation getUniverseSimulation() {
        return universeSimulation;
    }

    private CellsWrapper addCellsViewModel(ViewRectangularMap viewRectangularMap) {
        CellsWrapper cellsWrapper = new CellsWrapper();
        cellsWrapper.addEventListeners(viewRectangularMap.getArrayOfCells());
        return cellsWrapper;
    }

    public void removeLastMap() {
        getUniverseSimulation().removeLastMap();
        listOfStatistics.pollLast();
        listOfMapViews.pollLast();
    }

    public int getSize() {
        return listOfStatistics.size();
    }

    public void addNewMap() {
        universeSimulation.addNewMap();
        listOfStatistics.add(universeSimulation.getMapStatistics().getLast());
        ViewRectangularMap viewRectangularMap = new ViewRectangularMap();
        listOfMapViews.add(viewRectangularMap);
        CellsWrapper cellsWrapper = addCellsViewModel(viewRectangularMap);
        universeSimulation.addNewViewObserver(universeSimulation.getMapSimulations().get(universeSimulation.getMapSimulations().size() - 1), cellsWrapper);
    }

    public MapStatisticsGetter getNthMapStatisticsGetter(int mapNumber) {
        mapNumber -= 1;
        if (listOfStatistics.size() <= mapNumber) throw new IllegalArgumentException("There is no second map");
        return listOfStatistics.get(mapNumber).getMapSimulation().getMapStatisticsGetter();
    }

    public ViewRectangularMap getNthMapView(int mapNumber) {
        mapNumber -= 1;
        if (listOfMapViews.size() <= mapNumber) throw new IllegalArgumentException("There is no second map");
        return listOfMapViews.get(mapNumber);
    }

}
