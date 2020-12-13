package logic.statistics;

import logic.objects.animal.Animal;
import logic.objects.animal.FamilyMember;

import java.util.LinkedList;

public class AnimalStatistics {
    private final LinkedList<FamilyMember> childrenAnimalList;
    private final LinkedList<FamilyMember> parentsAnimalList;
    Animal animal;

    public AnimalStatistics(Animal animal) {
        this.animal = animal;
        childrenAnimalList = new LinkedList<>();
        parentsAnimalList = new LinkedList<>();
    }

    public void addToChildrenList(Animal child, int day) {
        childrenAnimalList.add(new FamilyMember(child, day));
    }

    public void addToParentsList(Animal parent1, Animal parent2, int day) {
        parentsAnimalList.add(new FamilyMember(parent1, day));
        parentsAnimalList.add(new FamilyMember(parent2, day));
    }

    public LinkedList<FamilyMember> getChildrenAnimalList() {
        return childrenAnimalList;
    }

    public LinkedList<FamilyMember> getParentsAnimalList() {
        return parentsAnimalList;
    }

    /**
     * Returns number of children in past n days
     *
     * @param n          days
     * @param currentDay current day
     * @return number of children
     */
    public int getChildrenNumber(int n, int currentDay) {
        int sum = 0;
        for (FamilyMember child : childrenAnimalList) {
            if (child.getDayWhenRelationshipStared() >= currentDay - n) {
                sum += 1;
            }
        }
        return sum;
    }


    /**
     * Should be wrapped using getAncestorsNumberWrapper()
     *
     * @param n          days
     * @param currentDay current day
     */
    public void getAncestorsNumber(int n, int currentDay, int[] sum) {
        sum[0] += childrenAnimalList.size();
        for (FamilyMember child : childrenAnimalList) {
            child.getAnimal().getAnimalStatistics().getAncestorsNumber(n, currentDay, sum);
        }
    }

    /**
     * Returns number of ancestors in last n days
     *
     * @param n          max days to check in past
     * @param currentDay current Day
     * @return number of ancestors
     */
    public int getAncestorsNumberWrapper(int n, int currentDay) {
        int[] sum = {0};
        getAncestorsNumber(n, currentDay, sum);
        return sum[0];
    }

}
