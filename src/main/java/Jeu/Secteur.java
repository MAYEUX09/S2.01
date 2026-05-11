package Jeu;

public class Secteur {
    private int ligne;
    private Mine Mine;
    private String Type;
    private boolean libreRobot;
    private int colonne;
    private boolean Libreelement;
    private Eau eau;
    public Robot Secteur;
    public Entrepot Secteur = LesSecteurs;
    public Mine Secteur;
    public Monde[] lesSecteurs;
    public Eau Secteur;
    public Terrain Secteur;

    /**
     * Default constructor
     */
    public Secteur(int ligne, Mine Mine, String Type, boolean libreRobot, int colonne, boolean Libreelement, Eau eau)
    {
        // Code réalisé par Ounays, à vérifier SVP
        this.ligne = ligne;
        this.Mine = Mine;
        this.Type = Type;
        this.libreRobot = libreRobot;
        this.colonne = colonne;
        this.Libreelement = Libreelement;
        this.eau = eau;
    }

    /**
     * @param Librerobot  
     * @param libreelement
     */
    public void estlibre(boolean Librerobot , bool libreelement ) {
        // Code réalisé par Ounays, à vérifier SVP
        if(Librerobot && Libreelement) {
            return False
        }
    }

    public Mine getMine() {
        // Code réalisé par Ounays, à vérifier SVP
        return Mine;
    }

    public Entrepot getEntrepot() {
        // Code réalisé par Ounays, à vérifier SVP
        return Entrepot
    }

    public Eau getEau() {
        // Code réalisé par Ounays, à vérifier SVP
        return eau;
    }
}