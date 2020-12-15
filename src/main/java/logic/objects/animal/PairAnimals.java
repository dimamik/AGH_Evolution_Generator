package logic.objects.animal;

import logic.maps.RectangularMap;
import logic.position.Vector2d;
import logic.random.RandomGenerator;

import static config.Config.*;

public class PairAnimals {
    public static void pairAnimalsFromFamilyGroup(Family family, RectangularMap rectangularMap, int dayOfParing) {
        Animal first = family.getFirst();
        Animal second = family.getSecond();
        Gens gens = calculateGens(first, second);
        Vector2d position = generatePositionForChild(rectangularMap);
        Animal tmpAnimal = new Animal(position, first.getEnergy() / 4 + second.getEnergy() / 4, gens);
        tmpAnimal.addToParents(first, second, dayOfParing);
        first.addChild(tmpAnimal, dayOfParing);
        second.addChild(tmpAnimal, dayOfParing);
        rectangularMap.addObject(tmpAnimal);
    }

    private static Gens calculateGens(Animal first, Animal second) {
        return new Gens(first.getGens().getGenSequence(), second.getGens().getGenSequence());
    }

    private static Vector2d generatePositionForChild(RectangularMap rectangularMap) {
        Vector2d position = RandomGenerator.getRandomPosition(WIDTH - 1, HEIGHT - 1);
        if (!rectangularMap.isMapFull()) {
            while (rectangularMap.isOccupied(position)) {
                position = RandomGenerator.getRandomPosition(WIDTH - 1, HEIGHT - 1);
            }
        }
        return position;
    }

    public static boolean canPair(Animal first, Animal second) {
        return first.getEnergy() >= START_ENERGY && second.getEnergy() >= START_ENERGY;
    }
}
