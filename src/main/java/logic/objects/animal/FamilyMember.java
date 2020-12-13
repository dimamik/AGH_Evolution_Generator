package logic.objects.animal;

/**
 * Class to contain family members of animal
 */
public class FamilyMember {
    private final Animal animal;
    private final int dayWhenRelationshipStared;

    /**
     * Can't be rebuild, because needs recursively know about all child's children
     * @param animal animal with which is the relation
     * @param dayWhenRelationshipStared day, when relation started
     */
    public FamilyMember(Animal animal, int dayWhenRelationshipStared) {
        this.animal = animal;
        this.dayWhenRelationshipStared = dayWhenRelationshipStared;
    }

    public int getDayWhenRelationshipStared() {
        return dayWhenRelationshipStared;
    }

    public Animal getAnimal() {
        return animal;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "animal=" + animal +
                ", dayWhenRelationshipStared=" + dayWhenRelationshipStared +
                '}';
    }
}
