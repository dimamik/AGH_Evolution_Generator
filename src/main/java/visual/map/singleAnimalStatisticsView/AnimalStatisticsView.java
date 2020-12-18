package visual.map.singleAnimalStatisticsView;

import logic.objects.animal.Animal;
import logic.objects.animal.Gens;

public class AnimalStatisticsView {
    int currentDay;
    private Animal animal;

    public AnimalStatisticsView(Animal animal, int day) {
        this.animal = animal;
        this.currentDay = day;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    /**
     * @return genome types list
     */
    public int[] getGenome() {
        Gens gensAnimal = getAnimal().getGens();

        return gensAnimal.GetGenomeTypes();
    }

    public double getAnimalKidsInNDays(int n) {
        return getAnimal().getAnimalStatistics().getChildrenNumber(n, currentDay);
    }

    public double getAnimalAncestorsInNDays(int n) {
        return getAnimal().getAnimalStatistics().getAncestorsNumberWrapper(n, currentDay);
    }
}
