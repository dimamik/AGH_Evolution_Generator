package logic.objects.animal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GensTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void childGenMaking() {
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        Gens genChild = new Gens(genSeq, genSeq2);
        assertTrue(genChild.isCorrect());

    }

    @Test
    void genAssertingChecking() {
        int[] genSeq = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq2 = {0, 0, 7, 1, 1, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        int[] genSeq3 = {0, 0, 7, 0, 0, 7, 2, 0, 2, 0, 2, 3, 7, 7, 7, 4, 4, 4, 4, 0, 7, 0, 7, 0, 5, 5, 6, 6, 7, 7, 7, 7};
        Gens genParent1 = new Gens(genSeq);
        Gens genParent2 = new Gens(genSeq2);
        assertTrue(genParent1.isCorrect());
        assertTrue(genParent2.isCorrect());
        assertFalse(new Gens(genSeq3).isCorrect());
    }

    @Test
    void generateMove() {
        int[] genSeq = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
        Gens gens = new Gens(genSeq);
        while (true) {
            int move = gens.generateMove();
            if (move == 5) {
                assertTrue(true);
                break;
            }
        }
    }
}