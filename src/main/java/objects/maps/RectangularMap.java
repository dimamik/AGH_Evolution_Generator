package objects.maps;

import objects.AbstractPositionedObject;
import objects.animal.Animal;
import objects.animal.FamilyGroup;
import objects.grass.GrassGenerator;
import position.Vector2d;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

import static config.Config.*;
import static objects.animal.PairAnimals.pairAnimalsFromFamilyGroup;

public class RectangularMap {
    int currentDay;

    /**
     * If Object is removed from cell -> Than we need to remove MapCell object from hashmap!
     */
    HashMap<Vector2d, MapCell> cellsHashMap;


    public RectangularMap() {
        this.currentDay = 0;
        this.cellsHashMap = new HashMap<>();
    }


    public boolean addObject(AbstractPositionedObject object){
        if (!cellsHashMap.containsKey(object.getPosition())){
            MapCell mapCell = new MapCell(object.getPosition());
            cellsHashMap.put(object.getPosition(), mapCell);
            mapCell.addObject(object);
            return true;
        }
        else{
            cellsHashMap.get(object.getPosition()).addObject(object);
        }
        return false;
    }

    /**
     * Returns whether a position on the map is occupied (Either by Animal/Paired
     * Animal or Grass)
     * @return
     */
    public boolean isOccupied(Vector2d position){
        if (cellsHashMap.containsKey(position)) {
            return true;
        }
        return false;
    }

    public boolean isMapFull(){
        int x = 0;
        int y = 0;
        while (x <= WIDTH) {
            while (y <= HEIGHT) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y)))
                    return false;
                if (y == HEIGHT / JUNGLE_RATIO)
                    y = HEIGHT - (HEIGHT / JUNGLE_RATIO) + 1;
                y++;
            }
            x++;
            if (x == WIDTH / JUNGLE_RATIO)
                x = WIDTH - (WIDTH / JUNGLE_RATIO) + 1;
        }
        return true;
    }

    public boolean isJungleFull(){
        for (int x = (WIDTH/JUNGLE_RATIO); x <= WIDTH-(WIDTH/JUNGLE_RATIO); x++) {
            for (int y = (HEIGHT / JUNGLE_RATIO); y < HEIGHT - (HEIGHT / JUNGLE_RATIO); y++) {
                if (!cellsHashMap.containsKey(new Vector2d(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveAnimal(Animal animal){
        removeObject(animal);
        animal.moveAnimal();
        addObject(animal);
    }

    public void removeObject(AbstractPositionedObject object){
        Vector2d position = object.getPosition();
        MapCell mapCell= cellsHashMap.get(position);
        mapCell.removeObject(object);
        if (mapCell.isEmpty()){
            cellsHashMap.remove(position);
        }
    }

    /**
     * Function to remove all dead Animals from the map
     * And to return list of alive animals to lower the complexity
     * of newDay method
     * @return Linked List of Alive Animals
     */
    public LinkedList<Animal> deleteDead(){
        LinkedList<Animal> listOfAnimals = new LinkedList<>();
        LinkedList<MapCell> listOfObjects = new LinkedList<>(cellsHashMap.values());
        for (MapCell currCell : listOfObjects) {
                listOfAnimals.addAll(currCell.getAllAnimalsAndRemoveDead());
                if (currCell.isEmpty()){
                    cellsHashMap.remove(currCell.getPosition());
                }
        }
        return listOfAnimals;
    }

    public void moveAllAnimals(LinkedList<Animal> listOfAnimals){
        for (Animal animal : listOfAnimals){
            moveAnimal(animal);
        }
    }

    public void eatByAnimals() {

        LinkedList<MapCell> listOfObjects = new LinkedList<>(cellsHashMap.values());
        for (MapCell mapCell : listOfObjects){
            mapCell.eatGrassByStrongestAnimal();
        }
    }

    public void pairAnimals() {
        LinkedList<MapCell> listOfObjects = new LinkedList<>(cellsHashMap.values());
        for (MapCell mapCell : listOfObjects){
           Optional<FamilyGroup> familyGroupOptional =  mapCell.pairAnimalsIfPossible();
           if (familyGroupOptional.isPresent()){
               //TODO Pair Animals given in FamilyGroup
               pairAnimalsFromFamilyGroup(familyGroupOptional.get(),this);
           }
        }
    }

    public void generateGrass(){
        GrassGenerator.generateGrasInJungles(this);
        GrassGenerator.generateGrassOutOfJungles(this);
    }

    public void newDay(){
        LinkedList<Animal> listOfAnimals = deleteDead();
        moveAllAnimals(listOfAnimals);
        eatByAnimals();
        pairAnimals();
        generateGrass();
    }

    public int getCurrentDay() {
        return currentDay;
    }
}
