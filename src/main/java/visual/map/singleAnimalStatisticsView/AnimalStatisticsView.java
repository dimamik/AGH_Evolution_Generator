package visual.map.singleAnimalStatisticsView;

import logic.objects.animal.Animal;
import logic.objects.animal.Gens;

public class AnimalStatisticsView {
    final long currentDay;
    private Animal animal;

    public AnimalStatisticsView(Animal animal, long day) {
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

    public double getAnimalKidsInNDays(long n) {
        return getAnimal().getAnimalStatistics().getChildrenNumber(n, currentDay);
    }

    public double getAnimalAncestorsInNDays(long n) {
        return getAnimal().getAnimalStatistics().getAncestorsNumberWrapper(n, currentDay);
    }
}
