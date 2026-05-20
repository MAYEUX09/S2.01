package Test;

import Jeu.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MineTest {

    protected Mine mine;

    @BeforeEach
    void setUp() {
        mine = new Mine(1, "OR", 10, 10);  // capaciteMax=10, stockDepart=10
    }

    @AfterEach
    void tearDown() {
        mine = null;
    }

    // Borne - 1 : quantiteSouhaitee (9) < quantiteRestante (10)
    @Test
    void testExtraire_BorneMoins1() {
        assertEquals(9, mine.extraire(9));
        assertEquals(1, mine.getCapaciteActuel());
    }

    // Borne exacte : quantiteSouhaitee (10) = quantiteRestante (10)
    @Test
    void testExtraire_BorneExacte() {
        assertEquals(10, mine.extraire(10));
        assertEquals(0, mine.getCapaciteActuel());
    }

    // Borne + 1 : quantiteSouhaitee (11) > quantiteRestante (10)
    @Test
    void testExtraire_BornePlus1() {
        assertEquals(10, mine.extraire(11));
        assertEquals(0, mine.getCapaciteActuel());
    }
}