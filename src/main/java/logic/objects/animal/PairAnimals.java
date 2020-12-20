package logic.objects.animal;

import logic.maps.RectangularMap;
import logic.position.MapDirection;
import logic.position.Vector2d;
import logic.random.RandomGenerator;

import java.util.Arrays;
import java.util.LinkedList;

import static config.Config.*;

public class PairAnimals {
    public static void pairAnimalsFromFamilyGroup(Family family, RectangularMap rectangularMap, long dayOfParing) {
        Animal first = family.getFirst();
        Animal second = family.getSecond();
        Gens gens = calculateGens(first, second);
        Vector2d position = generatePositionForChild(rectangularMap, first.getPosition());
        Animal tmpAnimal = new Animal(position, first.getEnergy() / 4 + second.getEnergy() / 4, gens);
        tmpAnimal.addToParents(first, second, dayOfParing);
        first.addChild(tmpAnimal, dayOfParing);
        second.addChild(tmpAnimal, dayOfParing);
        rectangularMap.addObject(tmpAnimal);
    }

    private static Gens calculateGens(Animal first, Animal second) {
        return new Gens(first.getGens().getGenSequence(), second.getGens().getGenSequence());
    }

    private static Vector2d generatePositionForChild(RectangularMap rectangularMap, Vector2d position) {
        Vector2d positionToReturn;

        LinkedList<MapDirection> mapDirectionLinkedList = new LinkedList<>(Arrays.asList(MapDirection.values()));
        while (mapDirectionLinkedList.size() > 0) {
            int randomIndex = RandomGenerator.getRandomNumberInRange(0, mapDirectionLinkedList.size() - 1);
            positionToReturn = position.addMirrored(mapDirectionLinkedList.get(randomIndex).toUnitVector());
            if (!rectangularMap.isOccupied(positionToReturn)) {
                return positionToReturn;
            } else {
                mapDirectionLinkedList.remove(randomIndex);
            }

        }
        positionToReturn = RandomGenerator.getRandomPosition(WIDTH - 1, HEIGHT - 1);
        if (!rectangularMap.isMapFull()) {
            while (rectangularMap.isOccupied(positionToReturn)) {
                positionToReturn = RandomGenerator.getRandomPosition(WIDTH - 1, HEIGHT - 1);
            }
        }
        return positionToReturn;
    }

    public static boolean canPair(Animal first, Animal second) {
        return first.getEnergy() >= START_ENERGY && second.getEnergy() >= START_ENERGY;
    }
}
