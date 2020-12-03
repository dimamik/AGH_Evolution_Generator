package objects.animal;

import objects.animal.Animal;

public class FamilyMember {
    Animal animal;
    int dayWhenRelationshipStared;

    public FamilyMember(Animal animal, int dayWhenRelationshipStared) {
        this.animal = animal;
        this.dayWhenRelationshipStared = dayWhenRelationshipStared;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "animal=" + animal +
                ", dayWhenRelationshipStared=" + dayWhenRelationshipStared +
                '}';
    }
}
