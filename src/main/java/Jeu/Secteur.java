package Jeu;

public class Secteur {
    private int ligne;
    private int colonne;
    private String Type;
    private boolean libreRobot;
    private boolean Libreelement;
    /**
     *
     *  Je sais pas quoi faire de ça
     *  public Robot Secteur;
     *  public Entrepot Secteur = LesSecteurs;
     *  public Mine Secteur;
     *  public Monde[] lesSecteurs;
     */
    private Eau eau;
    private Mine Mine;
    private Terrain terrain;
    private Entrepot entrepot;

    /**
     * Default constructor
     */
    public Secteur(Terrain terrain){
        this.terrain = terrain;
        this.eau = null;
        this.Mine = null;
        this.entrepot = null;
        this.libreRobot = true;
    }

    public Secteur(Eau eau){
        this.terrain = null;
        this.eau = eau;
        this.Mine = null;
        this.entrepot = null;
        this.libreRobot = false;
    }

    // Getters
    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Eau getEau() {
        return eau;
    }

    public Mine getMine() {
        return mine;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public boolean isLibreRobot() {
        return libreRobot;
    }

    // Setters
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public void setLibreRobot(boolean libreRobot) {
        this.libreRobot = libreRobot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return this.robot;
    }


    // Méthode
    // Savoir si le secteur est libre :
    public boolean estLibre() {
        return libreRobot && eau == null;
    }
}