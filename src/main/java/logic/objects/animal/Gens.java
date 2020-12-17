package logic.objects.animal;

import logic.random.RandomGenerator;

import java.util.Arrays;


public class Gens {

    private final int[] genSequence;
    private int[] types;

    /**
     * Basic Gens generating system to be initialized on the beginning
     *
     * @param genSequence gen sequence
     */
    public Gens(int[] genSequence) {
        Arrays.sort(genSequence);
        this.genSequence = genSequence;
        typesInit();
        if (!isCorrect()) {
            addMissingGens();
            System.out.println("NOT CORRECT DATA in Gens()");
            Arrays.sort(genSequence);
        }
    }


    /**
     * Gens generating in case of birth (Strict following git requirements, can be done better)
     *
     * @param parent1Gens gens of parent1
     * @param parent2Gens gens of parent2
     */
    public Gens(int[] parent1Gens, int[] parent2Gens) {
        genSequence = new int[32];
        typesInit();
        int divisionIndex = (int) (Math.random() * (31 + 1));
        int parentGivingTwoGroups = (int) (Math.random() * (1 + 1));
        if (parentGivingTwoGroups == 0) {
            if (divisionIndex >= 0) System.arraycopy(parent1Gens, 0, genSequence, 0, divisionIndex);
            if (32 - divisionIndex >= 0)
                System.arraycopy(parent2Gens, divisionIndex, genSequence, divisionIndex, 32 - divisionIndex);
        } else {
            if (divisionIndex >= 0) System.arraycopy(parent2Gens, 0, genSequence, 0, divisionIndex);
            if (32 - divisionIndex >= 0)
                System.arraycopy(parent1Gens, divisionIndex, genSequence, divisionIndex, 32 - divisionIndex);
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

    /**
     * Checks if gen sequence is correct
     *
     * @return true if is
     */
    public boolean isCorrect() {
        for (int type : types) {
            if (type <= 0) {
                return false;
            }
        }
        return true;
    }

    private int findTypePlace() {

        int placeToFindGenome = RandomGenerator.getRandomNumberInRange(0, 7);
        while (types[placeToFindGenome] <= 1) {
            placeToFindGenome = RandomGenerator.getRandomNumberInRange(0, 7);
        }
        return placeToFindGenome;
    }

    public int generateMove() {
        return genSequence[RandomGenerator.getRandomNumberInRange(0, 31)];
    }

    @Override
    public String toString() {
        return "Gens{" + "types=" + Arrays.toString(types) + '}';
    }

    public int[] getGenSequence() {
        return genSequence;
    }

    public int getDominantGenome() {
        int maxValue = Integer.MIN_VALUE;
        int genome = 0;
        int i;
        for (i = 0; i < 8; i++) {
            if (types[i] > maxValue) {
                maxValue = types[i];
                genome = i;
            }
        }
        return genome;
    }

    public int[] GetGenomeTypes() {
        return types;
    }
}