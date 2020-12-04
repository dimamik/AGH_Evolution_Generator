package objects.animal;

import objects.maps.RectangularMap;
import random.RandomGenerator;
import position.Vector2d;

import static config.Config.HEIGHT;
import static config.Config.WIDTH;

public class PairAnimals {
    public static void pairAnimalsFromFamilyGroup(FamilyGroup familyGroup, RectangularMap rectangularMap) {
        Animal first = familyGroup.first;
        Animal second = familyGroup.second;
        Gens gens = calculateGens(first, second);
        Vector2d position = generatePositionForChild(rectangularMap);
        Animal tmpAnimal = new Animal(position, first.getEnergy() / 4 + second.getEnergy() / 4, gens);
        tmpAnimal.addToParentsList(first, second, rectangularMap.getCurrentDay());
        first.addToChildrenList(tmpAnimal, rectangularMap.getCurrentDay());
        second.addToChildrenList(tmpAnimal, rectangularMap.getCurrentDay());
        rectangularMap.addObject(tmpAnimal);
    }

    private static Gens calculateGens(Animal first, Animal second) {
        return new Gens(first.getGens().getGenSequence(), second.getGens().getGenSequence());
    }

    private static Vector2d generatePositionForChild(RectangularMap rectangularMap) {
        Vector2d position = RandomGenerator.getRandomPosition(WIDTH, HEIGHT);
        if (!rectangularMap.isMapFull()) {
            while (rectangularMap.isOccupied(position)) {
                position = RandomGenerator.getRandomPosition(WIDTH, HEIGHT);
            }
        }
        return position;
    }
}
