package statistics;

import objects.animal.Animal;
import objects.animal.FamilyMember;

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
}
