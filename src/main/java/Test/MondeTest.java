package Test;
import Jeu.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MondeTest {
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
            assertEquals(3, robotOR.récolter(mineOR));
            assertEquals(3, robotOR.getStockActuel());
        }

        @Test
        void testRecolterMauvaisType() {
            assertEquals(0, robotOR.récolter(mineNI));
            assertEquals(0, robotOR.getStockActuel());
        }

        @Test
        void testRecolterSacPlein() {
            robotOR.récolter(mineOR); // stock = 3
            robotOR.récolter(mineOR); // stock = 6
            robotOR.récolter(mineOR); // stock = 7 (sac plein)
            assertEquals(0, robotOR.récolter(mineOR));
        }

        @Test
        void testRecolterMinePresqueVide() {
            Mine minePetite = new Mine(3, "OR", 50, 1);
            assertEquals(1, robotOR.récolter(minePetite));
        }

        @Test
        void testRecolterMineNull() {
            assertEquals(0, robotOR.récolter(null));
        }


        @Test
        void testDeposerNormal() {
            robotOR.récolter(mineOR); // stock = 3
            assertEquals(3, robotOR.déposer(entrepotOR));
            assertEquals(0, robotOR.getStockActuel());
            assertEquals(3, entrepotOR.getStockActuel());
        }

        @Test
        void testDeposerMauvaisType() {
            robotOR.récolter(mineOR); // stock = 3
            assertEquals(0, robotOR.déposer(entrepotNI));
            assertEquals(3, robotOR.getStockActuel());
        }

        @Test
        void testDeposerRobotVide() {
            assertEquals(0, robotOR.déposer(entrepotOR));
        }

        @Test
        void testDeposerEntrepotNull() {
            robotOR.récolter(mineOR);
            assertEquals(0, robotOR.déposer(null));
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
