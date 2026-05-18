

// ============================================================
//  EntrepotTest
// ============================================================

class EntrepotTest {

    protected Entrepot entrepot;

    // Tableau de test - méthode stocker()
    // | Cas | Stock initial | Quantité ajoutée | Stock final attendu | Critère            |
    // |-----|--------------|-----------------|---------------------|----------------------|
    // |  1  |      0       |       10        |         10          | premier dépôt        |
    // |  2  |     10       |        5        |         15          | dépôt cumulatif      |
    // |  3  |      0       |        0        |          0          | dépôt de 0           |

    @BeforeEach
    void setUp() {
        entrepot = new Entrepot(1, "OR", 5, 5);
    }

    @AfterEach
    void tearDown() {
        entrepot = null;
    }

    @Test
    void testStockerPremierDepot() {
        // Cas 1 : entrepôt vide, on stocke 10
        entrepot.stocker(10);
        assertEquals(10, entrepot.getStockActuel());
    }

    @Test
    void testStockerCumulatif() {
        // Cas 2 : deux dépôts successifs
        entrepot.stocker(10);
        entrepot.stocker(5);
        assertEquals(15, entrepot.getStockActuel());
    }

    @Test
    void testStockerZero() {
        // Cas 3 : dépôt de 0 ne change rien
        entrepot.stocker(0);
        assertEquals(0, entrepot.getStockActuel());
    }

    @Test
    void testStockInitialementVide() {
        assertEquals(0, entrepot.getStockActuel());
    }

    @Test
    void testGetId() {
        assertEquals(1, entrepot.getId());
    }

    @Test
    void testGetTypeMinerai() {
        assertEquals("OR", entrepot.getTypeMinerai());
    }

    @Test
    void testGetTypeMineraiNickel() {
        Entrepot entrepotNI = new Entrepot(2, "NI", 8, 2);
        assertEquals("NI", entrepotNI.getTypeMinerai());
    }
}


// ============================================================
//  SecteurTest
// ============================================================

class SecteurTest {

    protected Secteur secteurTerrain;
    protected Secteur secteurEau;

    @BeforeEach
    void setUp() {
        secteurTerrain = new Secteur(new Terrain(0));
        secteurEau     = new Secteur(new Eau());
    }

    @AfterEach
    void tearDown() {
        secteurTerrain = null;
        secteurEau     = null;
    }

    // --- Secteur terrain : valeurs initiales ---

    @Test
    void testTerrainPasEau() {
        assertNull(secteurTerrain.getEau());
    }

    @Test
    void testTerrainPasMine() {
        assertNull(secteurTerrain.getMine());
    }

    @Test
    void testTerrainPasEntrepot() {
        assertNull(secteurTerrain.getEntrepot());
    }

    @Test
    void testTerrainPasRobot() {
        assertNull(secteurTerrain.getRobot());
    }

    // --- Secteur eau ---

    @Test
    void testEauPresent() {
        assertNotNull(secteurEau.getEau());
    }

    @Test
    void testEauPasMine() {
        assertNull(secteurEau.getMine());
    }

    // --- setMine / getMine ---

    @Test
    void testSetEtGetMine() {
        Mine mine = new Mine(1, "OR", 80, 80);
        secteurTerrain.setMine(mine);
        assertEquals(mine, secteurTerrain.getMine());
    }

    @Test
    void testSupprimerMine() {
        Mine mine = new Mine(1, "OR", 80, 80);
        secteurTerrain.setMine(mine);
        secteurTerrain.setMine(null);
        assertNull(secteurTerrain.getMine());
    }

    // --- setEntrepot / getEntrepot ---

    @Test
    void testSetEtGetEntrepot() {
        Entrepot entrepot = new Entrepot(1, "NI", 5, 5);
        secteurTerrain.setEntrepot(entrepot);
        assertEquals(entrepot, secteurTerrain.getEntrepot());
    }

    // --- setRobot / getRobot ---

    @Test
    void testSetEtGetRobot() {
        Robot robot = new Robot(1, "OR", 7, 3, 0, 0);
        secteurTerrain.setRobot(robot);
        assertEquals(robot, secteurTerrain.getRobot());
    }

    @Test
    void testRobotEtMineSimultanes() {
        // Un secteur peut avoir une mine ET un robot en même temps
        Mine mine   = new Mine(1, "OR", 80, 80);
        Robot robot = new Robot(1, "OR", 7, 3, 2, 3);
        secteurTerrain.setMine(mine);
        secteurTerrain.setRobot(robot);
        assertNotNull(secteurTerrain.getMine());
        assertNotNull(secteurTerrain.getRobot());
    }

    @Test
    void testSupprimerRobot() {
        Robot robot = new Robot(1, "OR", 7, 3, 0, 0);
        secteurTerrain.setRobot(robot);
        secteurTerrain.setRobot(null);
        assertNull(secteurTerrain.getRobot());
    }
}