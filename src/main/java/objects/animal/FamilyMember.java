package objects.animal;

public class FamilyMember {
    //TODO Rebuild Family Member to store only needed data
    private final Animal animal;
    private final int dayWhenRelationshipStared;

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
