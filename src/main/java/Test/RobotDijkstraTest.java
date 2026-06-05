package Test;

import Jeu.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RobotDijkstraTest {

    protected Robot robot;
    protected Secteur[][] grille;

    @BeforeEach
    void setUp() {
        // Grille vide 10x10
        grille = new Secteur[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                grille[i][j] = new Secteur(new Terrain(i * 10 + j));

        // Robot placé en (5,5) par défaut
        robot = new Robot(1, "OR", 7, 3, 5, 5);
        grille[5][5].setRobot(robot);
    }

    @AfterEach
    void tearDown() {
        robot = null;
        grille = null;
    }

    // - Cas 1 : L25 - u.equals(target) = Vrai dès le départ (source == cible)
    // Aucun chemin à construire donc return null
    @Test
    void T1_testDijkstra_SourceEgaleCible() {
        String resultat = robot.executerDijkstra(grille, 5, 5);
        assertNull(resultat);
    }

    // - Cas 2 : L38 - bornes valides = Vrai, chemin direct vers l'est
    // C1=F, C2=V, C3=F, C4=F, C5=V, C6=V, C7=F, C8=F, C9=F, C10=V
    @Test
    void T2_testDijkstra_CheminLibreVersEst() {
        String resultat = robot.executerDijkstra(grille, 5, 6);
        assertEquals("est", resultat);
    }

    // - Cas 3 : L38 - bornes valides = Vrai, chemin direct vers l'ouest
    // C10=F, C11=V
    @Test
    void T3_testDijkstra_CheminLibreVersOuest() {
        String resultat = robot.executerDijkstra(grille, 5, 4);
        assertEquals("ouest", resultat);
    }

    // - Cas 4 : L38 - bornes valides = Vrai, chemin direct vers le nord
    // C8=V
    @Test
    void T4_testDijkstra_CheminLibreVersNord() {
        String resultat = robot.executerDijkstra(grille, 4, 5);
        assertEquals("nord", resultat);
    }

    // - Cas 5 : L38 - bornes valides = Vrai, chemin direct vers le sud
    // C8=F, C9=V
    @Test
    void T5_testDijkstra_CheminLibreVersSud() {
        String resultat = robot.executerDijkstra(grille, 6, 5);
        assertEquals("sud", resultat);
    }

    // - Cas 6 : L41 - eau présente = Vrai (C3=V)
    // Eau sur (5,6), chemin doit contourner -> passe par (4,5) -> (4,6) -> (5,6)...
    // Mais cible (5,7) accessible par contournement -> premier pas = "nord"
    @Test
    void T6_testDijkstra_EauContournée() {
        grille[5][6] = new Secteur(new Eau());
        String resultat = robot.executerDijkstra(grille, 5, 7);
        assertNotNull(resultat);
        assertNotEquals("est", resultat);
    }

    // - Cas 7 : L42 - robot présent et v != target (C4=V) -> obstacle
    // Robot sur (5,6), cible en (5,7) -> doit contourner
    @Test
    void T7_testDijkstra_ContournementRobot() {
        Robot autreRobot = new Robot(2, "NI", 7, 3, 5, 6);
        grille[5][6].setRobot(autreRobot);
        String resultat = robot.executerDijkstra(grille, 5, 7);
        assertNotNull(resultat);
        assertTrue(resultat.equals("nord") || resultat.equals("sud"));
    }

    // - Cas 8 : L42 - robot présent mais v == target (C4=F, C7=F)
    // Robot directement sur la cible -> doit quand même y aller (cible autorisée)
    @Test
    void T8_testDijkstra_RobotSurCible() {
        Robot autreRobot = new Robot(2, "NI", 7, 3, 5, 6);
        grille[5][6].setRobot(autreRobot);
        String resultat = robot.executerDijkstra(grille, 5, 6);
        assertEquals("est", resultat);
    }

    // - Cas 9 : L62 - prev.get(target) == null (C7=V)
    // Cible complètement entourée d'eau -> chemin impossible -> return null
    @Test
    void T9_testDijkstra_CheminImpossible() {
        // Entourer la cible (5,8) d'eau de toutes parts
        grille[4][8] = new Secteur(new Eau());
        grille[6][8] = new Secteur(new Eau());
        grille[5][7] = new Secteur(new Eau());
        grille[5][9] = new Secteur(new Eau());
        String resultat = robot.executerDijkstra(grille, 5, 8);
        assertNull(resultat);
    }

    // - Cas 10 : L49 - alt < dist(v) = Vrai et F (C6=V et C6=F)
    // Chemin long
    @Test
    void T10_testDijkstra_CheminLong() {
        String resultat = robot.executerDijkstra(grille, 5, 9);
        assertEquals("est", resultat);
    }
}