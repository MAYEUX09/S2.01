package Jeu;

public class Secteur {
    private Terrain terrainDeBase;
    private Eau elementEau;
    private Mine gisementMine;
    private Entrepot structureEntrepot;
    private Robot robotOccupant; // Un seul robot par secteur à la fois

    public Secteur(Terrain terrain) {
        this.terrainDeBase = terrain;
    }

    public Secteur(Eau eau) {
        this.elementEau = eau;
    }

// ===============================================
// Getters
// ===============================================

    public Eau getEau() {
        return elementEau;
    }

    public Mine getMine() {
        return gisementMine;
    }

    public Entrepot getEntrepot() {
        return structureEntrepot;
    }

    public Robot getRobot() {
        return robotOccupant;
    }

    public Terrain getTerrain() {
        return terrainDeBase;
    }

// ===============================================
// Setters
// ===============================================

    public void setMine(Mine mine) {
        this.gisementMine = mine;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.structureEntrepot = entrepot;
    }

    public void setRobot(Robot robot) {
        this.robotOccupant = robot;
    }
}