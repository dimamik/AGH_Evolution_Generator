package objects.animal;

public class Family {

    private Animal first;
    private Animal second;

    public Family(Animal first, Animal second) {
        this.first = first;
        this.second = second;
    }

    public Animal getFirst() {
        return first;
    }

    public Animal getSecond() {
        return second;
    }
}
