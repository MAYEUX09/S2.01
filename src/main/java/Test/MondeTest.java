package Test;

import Jeu.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

class MondeTest {

    protected Monde monde;
    protected Secteur[][] grille;
    protected ArrayList<Robot> lesRobots;

    @BeforeEach
    void setUp() {
        monde = new Monde();

        grille = new Secteur[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                grille[i][j] = new Secteur(new Terrain(i * 10 + j));

        lesRobots = new ArrayList<>();

        monde.setGrille(grille);
        monde.setLesRobots(lesRobots);
        monde.setNumeroTour(1);
    }

    @AfterEach
    void tearDown() throws Exception {
        monde = null;
        grille = null;
        lesRobots = null;
    }

    // Cas 1 - C1=V : action "recolter" -> recolter() appelé
    @Test
    void testTour1() {
        Mine mine = new Mine(1, "OR", 80, 80);
        Robot robot = new Robot(1, "OR", 7, 3, 5, 5);
        grille[5][5].setMine(mine);
        grille[5][5].setRobot(robot);
        lesRobots.add(robot);

        System.setIn(new ByteArrayInputStream("recolter\n".getBytes()));
        monde.tour();

        assertEquals(3, robot.getStockActuel());
        assertEquals(77, mine.getCapaciteActuel());
    }

    // Cas 2 - C1=F, C2=V : action "deposer" -> deposer() appelé
    @Test
    void testTour2() {
        Mine mine = new Mine(1, "OR", 80, 80);
        Entrepot entrepot = new Entrepot(1, "OR", 5, 5);
        Robot robot = new Robot(1, "OR", 7, 3, 5, 5);
        grille[5][5].setMine(mine);
        grille[5][5].setEntrepot(entrepot);
        grille[5][5].setRobot(robot);
        lesRobots.add(robot);

        // On récolte d'abord pour avoir du stock à déposer
        robot.recolter(mine);

        System.setIn(new ByteArrayInputStream("deposer\n".getBytes()));
        monde.tour();

        assertEquals(0, robot.getStockActuel());
        assertEquals(3, entrepot.getStockActuel());
    }

    // Cas 3 - C1=F, C2=F, C3=V, C4=V, C5=V : déplacement possible -> robot déplacé
    @Test
    void testTour3() {
        Robot robot = new Robot(1, "OR", 7, 3, 5, 5);
        grille[5][5].setRobot(robot);
        lesRobots.add(robot);

        System.setIn(new ByteArrayInputStream("est\n".getBytes()));
        monde.tour();

        assertNull(grille[5][5].getRobot());
        assertEquals(robot, grille[5][6].getRobot());
        assertEquals(5, robot.getLigne());
        assertEquals(6, robot.getColonne());
    }

    // Cas 4 - C1=F, C2=F, C3=F : hors limites -> mouvement impossible
    @Test
    void testTour4() {
        Robot robot = new Robot(1, "OR", 7, 3, 0, 0);
        grille[0][0].setRobot(robot);
        lesRobots.add(robot);

        System.setIn(new ByteArrayInputStream("ouest\n".getBytes()));
        monde.tour();

        assertEquals(robot, grille[0][0].getRobot());
        assertEquals(0, robot.getLigne());
        assertEquals(0, robot.getColonne());
    }

    // Cas 5 - C1=F, C2=F, C3=V, C4=F : case d'eau -> mouvement impossible
    @Test
    void testTour5() {
        Robot robot = new Robot(1, "OR", 7, 3, 4, 4);
        grille[4][4].setRobot(robot);
        grille[4][5] = new Secteur(new Eau());
        lesRobots.add(robot);

        System.setIn(new ByteArrayInputStream("est\n".getBytes()));
        monde.tour();

        assertEquals(robot, grille[4][4].getRobot());
        assertEquals(4, robot.getLigne());
        assertEquals(4, robot.getColonne());
    }

    // Cas 6 - C1=F, C2=F, C3=V, C4=V, C5=F : case occupée par un robot -> mouvement impossible
    @Test
    void testTour6() {
        Robot robot1 = new Robot(1, "OR", 7, 3, 3, 3);
        Robot robot2 = new Robot(2, "NI", 7, 3, 3, 4);
        grille[3][3].setRobot(robot1);
        grille[3][4].setRobot(robot2);
        lesRobots.add(robot1);

        System.setIn(new ByteArrayInputStream("est\n".getBytes()));
        monde.tour();

        assertEquals(robot1, grille[3][3].getRobot());
        assertEquals(robot2, grille[3][4].getRobot());
        assertEquals(3, robot1.getLigne());
        assertEquals(3, robot1.getColonne());
    }
}