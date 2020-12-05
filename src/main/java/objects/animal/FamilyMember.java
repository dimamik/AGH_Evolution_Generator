package objects.animal;

public class FamilyMember {
    //TODO it is better not to have animal in there
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
