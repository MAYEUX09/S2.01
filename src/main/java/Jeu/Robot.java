package Jeu;

public class Robot {
    private int identifiant;
    private String typeDeSpecialisation; // "NI" ou "OR"
    private int capaciteStockageMaximale;
    private int puissanceExtraction;
    private int quantiteStockeeActuellement;
    private int positionLigne;
    private int positionColonne;
    private int credit;


    public Robot(int id, String type, int stockageMax, int extraction, int ligne, int colonne) {
        this.identifiant = id;
        this.typeDeSpecialisation = type;
        this.capaciteStockageMaximale = stockageMax;
        this.puissanceExtraction = extraction;
        this.positionLigne = ligne;
        this.positionColonne = colonne;
        this.quantiteStockeeActuellement = 0;
        this.credit = 1;
    }

    public void avancer(String direction) {
        if (direction.equals("nord")) {
            this.positionLigne--;
            this.credit--;
        } else if (direction.equals("sud")) {
            this.positionLigne++;
            this.credit--;
        } else if (direction.equals("est")) {
            this.positionColonne++;
            this.credit--;
        } else if (direction.equals("ouest")) {
            this.positionColonne--;
            this.credit--;
        }
    }


    public int recolter(Mine mineCible) {
        if (mineCible != null && mineCible.getTypeMinerai().equals(this.typeDeSpecialisation)) {
            int espaceLibreDansLeSac = this.capaciteStockageMaximale - this.quantiteStockeeActuellement;
            int quantiteAExtraire = Math.min(this.puissanceExtraction, espaceLibreDansLeSac);

            int quantiteRecoltee = mineCible.extraire(quantiteAExtraire);
            this.quantiteStockeeActuellement += quantiteRecoltee;
            return quantiteRecoltee;
        }
        return 0;
    }

    public int deposer(Entrepot entrepotCible) {
        if (entrepotCible != null && entrepotCible.getTypeMinerai().equals(this.typeDeSpecialisation)) {
            int quantiteADeposer = this.quantiteStockeeActuellement;
            entrepotCible.stocker(quantiteADeposer);
            this.quantiteStockeeActuellement = 0;
            return quantiteADeposer;
        }
        return 0;
    }

    public int getId() {
        return identifiant;
    }

    public String getType() {
        return typeDeSpecialisation;
    }

    public int getLigne() {
        return positionLigne;
    }

    public int getColonne() {
        return positionColonne;
    }

    public int getStockActuel() {
        return quantiteStockeeActuellement;
    }

    public int getStockage() {
        return capaciteStockageMaximale;
    }

    public int getCredit(){return credit;}

    public void resetcredit(){this.credit = 1;}

    @Override
    public String toString() {
        return "Robot " + this.identifiant + " Spé : " + this.typeDeSpecialisation;
    }
}