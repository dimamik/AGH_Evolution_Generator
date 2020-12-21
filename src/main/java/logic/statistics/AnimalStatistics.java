package logic.statistics;

import logic.objects.animal.Animal;
import logic.objects.animal.FamilyMember;

import java.util.LinkedList;

public class AnimalStatistics {
    private final LinkedList<FamilyMember> childrenAnimalList;
    private final LinkedList<FamilyMember> parentsAnimalList;

    public AnimalStatistics() {
        childrenAnimalList = new LinkedList<>();
        parentsAnimalList = new LinkedList<>();
    }

    public void addToChildrenList(Animal child, long day) {
        childrenAnimalList.add(new FamilyMember(child, day));
    }

    public void addToParentsList(Animal parent1, Animal parent2, long day) {
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
    public int getChildrenNumber(long n, long currentDay) {
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
    public void getDescendantsNumber(long n, long currentDay, int[] sum) {
        if (n <= 0)
            return;
        sum[0] += childrenAnimalList.size();
        for (FamilyMember child : childrenAnimalList) {
            child.getAnimal().getAnimalStatistics().getDescendantsNumber(n - (currentDay - child.getDayWhenRelationshipStared()), currentDay, sum);
        }
    }

    /**
     * Returns number of Descendants in last n days
     *
     * @param n          max days to check in past
     * @param currentDay current Day
     * @return number of ancestors
     */
    public int getAncestorsNumberWrapper(long n, long currentDay) {
        int[] sum = {0};
        getDescendantsNumber(n, currentDay, sum);
        return sum[0];
    }

}
