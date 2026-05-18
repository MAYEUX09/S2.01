package Test;
import Jeu.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MondeTest {
        private Mine mineOr;
        private Mine mineNickel;
        private Entrepot entrepotOr;
        private Entrepot entrepotNickel;
        private Robot robotOr;
        private Robot robotNickel;
        private Monde monde;

    @Test
    void testCreationMineOR() {
        assertEquals(1, mineOR.getId());
        assertEquals("OR", mineOR.getTypeMinerai());
        assertEquals(80, mineOR.getCapaciteActuel());
        assertEquals(80, mineOR.getCapaciteMax());
    }

    @Test
    void testExtractionNormaleMine() {
        int quantiteExtraite = mineOR.extraire(10);

        assertEquals(10, quantiteExtraite);
        assertEquals(70, mineOR.getCapaciteActuel());
    }

    @Test
    void testExtractionSuperieureStockMine() {
        int quantiteExtraite = mineNI.extraire(100);

        assertEquals(60, quantiteExtraite);
        assertEquals(0, mineNI.getCapaciteActuel());
    }

    @Test
    void testDeplacementRobotVersEst() {
        robotOR.avancer("est");

        assertEquals(0, robotOR.getLigne());
        assertEquals(1, robotOR.getColonne());
    }

    @Test
    void testDeplacementRobotVersSud() {
        robotOR.avancer("sud");

        assertEquals(1, robotOR.getLigne());
        assertEquals(0, robotOR.getColonne());
    }

    @Test
    void testRecolteBonneMine() {
        int recolte = robotOR.recolter(mineOR);

        assertEquals(3, recolte);
        assertEquals(3, robotOR.getStockActuel());
        assertEquals(77, mineOR.getCapaciteActuel());
    }

    @Test
    void testRecolteMauvaisTypeMine() {
        int recolte = robotOR.recolter(mineNI);

        assertEquals(0, recolte);
        assertEquals(0, robotOR.getStockActuel());
        assertEquals(60, mineNI.getCapaciteActuel());
    }

    @Test
    void testRecolteJusquaCapaciteMaxRobot() {
        int recolte1 = robotOR.recolter(mineOR);
        int recolte2 = robotOR.recolter(mineOR);
        int recolte3 = robotOR.recolter(mineOR);

        assertEquals(3, recolte1);
        assertEquals(3, recolte2);
        assertEquals(1, recolte3);
        assertEquals(7, robotOR.getStockActuel());
        assertEquals(73, mineOR.getCapaciteActuel());
    }

    @Test
    void testDeposerDansBonEntrepot() {
        robotOR.recolter(mineOR);
        int quantiteDeposee = robotOR.deposer(entrepotOR);

        assertEquals(3, quantiteDeposee);
        assertEquals(0, robotOR.getStockActuel());
        assertEquals(3, entrepotOR.getStockActuel());
    }

    @Test
    void testDeposerDansMauvaisEntrepot() {
        robotOR.recolter(mineOR);
        int quantiteDeposee = robotOR.deposer(entrepotNI);

        assertEquals(0, quantiteDeposee);
        assertEquals(3, robotOR.getStockActuel());
        assertEquals(0, entrepotNI.getStockActuel());
    }

    @Test
    void testDeposerSacVide() {
        int quantiteDeposee = robotOR.deposer(entrepotOR);

        assertEquals(0, quantiteDeposee);
        assertEquals(0, robotOR.getStockActuel());
        assertEquals(0, entrepotOR.getStockActuel());
    }

    @Test
    void testStockageEntrepot() {
        entrepotOR.stocker(5);
        entrepotOR.stocker(2);

        assertEquals(7, entrepotOR.getStockActuel());
    }

    @Test
    void testSecteurTerrain() {
        assertNotNull(secteurTerrain.getTerrain());
        assertNull(secteurTerrain.getEau());
        assertNull(secteurTerrain.getMine());
        assertNull(secteurTerrain.getEntrepot());
        assertNull(secteurTerrain.getRobot());
    }

    @Test
    void testSecteurEau() {
        assertNotNull(secteurEau.getEau());
        assertNull(secteurEau.getTerrain());
        assertNull(secteurEau.getMine());
        assertNull(secteurEau.getEntrepot());
        assertNull(secteurEau.getRobot());
    }

    @Test
    void testAjouterMineEntrepotRobotDansSecteurTerrain() {
        secteurTerrain.setMine(mineOR);
        secteurTerrain.setEntrepot(entrepotOR);
        secteurTerrain.setRobot(robotOR);

        assertEquals(mineOR, secteurTerrain.getMine());
        assertEquals(entrepotOR, secteurTerrain.getEntrepot());
        assertEquals(robotOR, secteurTerrain.getRobot());
    }

// Partie 2
    private Mine mineOR;
    private Mine mineNI;
    private Robot robotOR;
    private Entrepot entrepotOR;
    private Entrepot entrepotNI;
    private Secteur secteurTerrain;
    private Secteur secteurEau;

    @BeforeEach
    void setUp() {
        mineOR         = new Mine(1, "OR", 80, 80);
        mineNI         = new Mine(2, "NI", 60, 60);
        robotOR        = new Robot(1, "OR", 7, 3, 0, 0);
        entrepotOR     = new Entrepot(1, "OR", 5, 5);
        entrepotNI     = new Entrepot(2, "NI", 8, 2);
        secteurTerrain = new Secteur(new Terrain(0));
        secteurEau     = new Secteur(new Eau());
    }

    @AfterEach
    void tearDown() {
        mineOR = null; mineNI = null;
        robotOR = null;
        entrepotOR = null; entrepotNI = null;
        secteurTerrain = null; secteurEau = null;
    }

        //Mine

    @Test
    void testExtraireNormal() {
            assertEquals(5, mineOR.extraire(5));
            assertEquals(75, mineOR.getCapaciteActuel());
        }

        @Test
        void testExtraireExact() {
            assertEquals(80, mineOR.extraire(80));
            assertEquals(0, mineOR.getCapaciteActuel());
        }

        @Test
        void testExtrairePartiel() {
            Mine minePetite = new Mine(3, "OR", 50, 3);
            assertEquals(3, minePetite.extraire(5));
            assertEquals(0, minePetite.getCapaciteActuel());
        }

        @Test
        void testExtraireMineVide() {
            Mine mineVide = new Mine(4, "OR", 50, 0);
            assertEquals(0, mineVide.extraire(5));
        }

        @Test
        void testExtraireZero() {
            assertEquals(0, mineOR.extraire(0));
            assertEquals(80, mineOR.getCapaciteActuel());
        }

        //Robot

        @Test
        void testRecolterNormal() {
            assertEquals(3, robotOR.recolter(mineOR));
            assertEquals(3, robotOR.getStockActuel());
        }

        @Test
        void testRecolterMauvaisType() {
            assertEquals(0, robotOR.recolter(mineNI));
            assertEquals(0, robotOR.getStockActuel());
        }

        @Test
        void testRecolterSacPlein() {
            robotOR.recolter(mineOR); // stock = 3
            robotOR.recolter(mineOR); // stock = 6
            robotOR.recolter(mineOR); // stock = 7 (sac plein)
            assertEquals(0, robotOR.recolter(mineOR));
        }

        @Test
        void testRecolterMinePresqueVide() {
            Mine minePetite = new Mine(3, "OR", 50, 1);
            assertEquals(1, robotOR.recolter(minePetite));
        }

        @Test
        void testRecolterMineNull() {
            assertEquals(0, robotOR.recolter(null));
        }

        @Test
        void testDeposerNormal() {
            robotOR.recolter(mineOR); // stock = 3
            assertEquals(3, robotOR.deposer(entrepotOR));
            assertEquals(0, robotOR.getStockActuel());
            assertEquals(3, entrepotOR.getStockActuel());
        }

        @Test
        void testDeposerMauvaisType() {
            robotOR.recolter(mineOR); // stock = 3
            assertEquals(0, robotOR.deposer(entrepotNI));
            assertEquals(3, robotOR.getStockActuel());
        }

        @Test
        void testDeposerRobotVide() {
            assertEquals(0, robotOR.deposer(entrepotOR));
        }

        @Test
        void testDeposerEntrepotNull() {
            robotOR.recolter(mineOR);
            assertEquals(0, robotOR.deposer(null));
            assertEquals(3, robotOR.getStockActuel());
        }

        @Test
        void testAvancerNord() {
            robotOR.avancer("nord");
            assertEquals(-1, robotOR.getLigne());
        }

        @Test
        void testAvancerSud() {
            robotOR.avancer("sud");
            assertEquals(1, robotOR.getLigne());
        }

        @Test
        void testAvancerEst() {
            robotOR.avancer("est");
            assertEquals(1, robotOR.getColonne());
        }

        @Test
        void testAvancerOuest() {
            robotOR.avancer("ouest");
            assertEquals(-1, robotOR.getColonne());
        }

        //Entrepot

        @Test
        void testStockerPremierDepot() {
            entrepotOR.stocker(10);
            assertEquals(10, entrepotOR.getStockActuel());
        }

        @Test
        void testStockerCumulatif() {
            entrepotOR.stocker(10);
            entrepotOR.stocker(5);
            assertEquals(15, entrepotOR.getStockActuel());
        }

        @Test
        void testStockerZero() {
            entrepotOR.stocker(0);
            assertEquals(0, entrepotOR.getStockActuel());
        }

        //Secteur

        @Test
        void testSecteurTerrainVide() {
            assertNull(secteurTerrain.getMine());
            assertNull(secteurTerrain.getEntrepot());
            assertNull(secteurTerrain.getRobot());
            assertNull(secteurTerrain.getEau());
        }

        @Test
        void testSecteurEauPresente() {
            assertNotNull(secteurEau.getEau());
        }

        @Test
        void testSecteurSetMine() {
            secteurTerrain.setMine(mineOR);
            assertEquals(mineOR, secteurTerrain.getMine());
        }

        @Test
        void testSecteurSetRobot() {
            secteurTerrain.setRobot(robotOR);
            assertEquals(robotOR, secteurTerrain.getRobot());
        }

        @Test
        void testSecteurMineEtRobotSimultanes() {
            secteurTerrain.setMine(mineOR);
            secteurTerrain.setRobot(robotOR);
            assertNotNull(secteurTerrain.getMine());
            assertNotNull(secteurTerrain.getRobot());
    }
}
