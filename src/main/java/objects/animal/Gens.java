package objects.animal;

import java.util.Arrays;

import static objects.random.RandomGenerator.getRandomNumberInRange;


public class Gens {

    private int[] genSequence;
    private int[] types;

    /**
     * Basic Gens generating system to be initialized on the beginning
     *
     * @param genSequence
     */
    public Gens(int[] genSequence) {
        Arrays.sort(genSequence);
        this.genSequence = genSequence;
        typesInit();
        if (!isCorrect()) {
            System.out.println("NOT CORRECT DATA in Gens()");
            this.genSequence = new int[] { 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5,
                    6, 6, 7, 7, 7, 7 };
            Arrays.sort(genSequence);
        }
    }

    /**
     * Gens generating in case of birth
     *
     * @param parent1Gens
     * @param parent2Gens
     */
    public Gens(int[] parent1Gens, int[] parent2Gens) {
        genSequence = new int[32];
        typesInit();
        int divisionIndex = (int) (Math.random() * (31 + 1));
        int parentGivingTwoGroups = (int) (Math.random() * (1 + 1));
        if (parentGivingTwoGroups == 0) {
            for (int i = 0; i < divisionIndex; i++) {
                genSequence[i] = parent1Gens[i];
            }
            for (int i = divisionIndex; i < 32; i++) {
                genSequence[i] = parent2Gens[i];
            }
        } else {
            for (int i = 0; i < divisionIndex; i++) {
                genSequence[i] = parent2Gens[i];
            }
            for (int i = divisionIndex; i < 32; i++) {
                genSequence[i] = parent1Gens[i];
            }
        }
        typesInit();

        addMissingGens();

    }

    private void typesInit() {
        types = new int[8];
        for (int i : genSequence) {
            types[i] += 1;
        }
    }

    private void addMissingGens() {
        for (int i = 0; i < 8; i++) {
            if (types[i] <= 0) {
                types[findTypePlace()] -= 1;
                types[i] += 1;
            }
        }
        int j = 0;
        for (int i = 0; i < 8; i++) {
            while (types[i] > 0) {
                genSequence[j++] = i;
                types[i] -= 1;
            }
        }
        for (int i : genSequence) {
            types[i] += 1;
        }
    }

    public boolean isCorrect() {
        for (int i = 0; i < types.length; i++) {
            if (types[i] <= 0) {
                return false;
            }
        }
        return true;
    }

    private int findTypePlace() {

        int placeToFindGenome = getRandomNumberInRange(0, 7);
        while (types[placeToFindGenome] <= 1) {
            placeToFindGenome = getRandomNumberInRange(0, 7);
        }
        return placeToFindGenome;
    }

    public int generateMove() {
        return genSequence[getRandomNumberInRange(0, 31)];
    }

    @Override
    public String toString() {
        return "Gens{" + "types=" + Arrays.toString(types) + '}';
    }

    public int[] getGenSequence() {
        return genSequence;
    }
}