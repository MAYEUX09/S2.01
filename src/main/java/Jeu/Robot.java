package Jeu;

import java.util.Objects;

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

    // SAE2.02
    public String modedurobot (){
        if (this.quantiteStockeeActuellement < this.capaciteStockageMaximale){
            return "Mine";
        }
        else {
            return "Entrepot";
        }
    }

    public String[][] generergrille(Secteur[][] grille){
        String[][] grilletempo = new String[10][10];
        for (int i = 0; i < grilletempo.length; i++){
            for (int j = 0; j < grilletempo.length; j++){
                if (grille[i][j].getEau() != null){
                    grilletempo[i][j] = "XX    ";
                }
                if (grille[i][j].getEntrepot() != null && grille[i][j].getEntrepot().getTypeMinerai().equals("NI")){
                    grilletempo[i][j] = "eNI";
                }
                if (grille[i][j].getEntrepot() != null && grille[i][j].getEntrepot().getTypeMinerai().equals("OR")){
                    grilletempo[i][j] = "eOR";
                }
                if (grille[i][j].getMine() != null && grille[i][j].getMine().getTypeMinerai().equals("NI")){
                    grilletempo[i][j] = "mNI";
                }
                if (grille[i][j].getMine() != null && grille[i][j].getMine().getTypeMinerai().equals("OR")){
                    grilletempo[i][j] = "mOR";
                }
                if (grille[i][j].getRobot() != null && grille[i][j].getRobot().getType().equals("OR")){
                    grilletempo[i][j] = "rOR";
                }
                if(grille[i][j].getRobot() != null && grille[i][j].getRobot().getType().equals("NI")){
                    grilletempo[i][j] = "rNI";
                }
            }
        }
        return grilletempo;
    }

    public void avancerautomatiquement(Secteur[][] grille){
        String mode = modedurobot();
        String[][] grilletempo = new String[10][10];
        grilletempo = generergrille(grille);
        if (mode.equals("Mine")){

        }
        else{

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