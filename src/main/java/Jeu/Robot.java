package Jeu;

import java.util.*;

public class Robot {
    private String nom;
    private int Stockage;
    private int Capaciteex;
    private int id;
    private int ligne;
    private int colonne;
    private String Specialite;
    private int stockageactuel;
    private Monde monde;
    private Secteur secteur;



    public Robot(String nom, int stockage, int capaciteex, int id, int ligne, int colonne, String specialite, Monde monde, Secteur secteur) {
        this.nom = nom;
        this.Stockage = stockage;
        this.Capaciteex = capaciteex;
        this.id = id;
        this.ligne = ligne;
        this.colonne = colonne;
        this.Specialite = specialite;
        this.stockageactuel = 0;
        this.monde = monde;
        this.secteur = secteur;

    }

public String getNom() {
    return nom;
}

public int getStockage() {
    return Stockage;
}
public int getCapaciteex() {
        return Capaciteex;
}

public int getId() {
        return id;
}

public int getLigne() {
        return ligne;
}

public int getColonne() {
        return colonne;
}

public String getSpecialite() {
        return Specialite;
}

public int getStockageactuel() {
        return stockageactuel;
}

public Monde getMonde() {
        return monde;
}

public Secteur getSecteur() {
        return secteur;
}

public void setLigne(int ligne) {
        this.ligne = ligne;
}

public void setColonne(int colonne) {
        this.colonne = colonne;
}

public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
}

public void setStockageactuel (int stockageactuel) {
        this.stockageactuel = stockageactuel;
}

public void avancer() {
        int nouvelleLigne = ligne;
        int nouvelleColonne = colonne;




}


    public boolean Avancer(void String) {
        // TODO implement here
        return false;
    }

    /**
     * 
     */
    public void Recolter() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Deposer() {
        // TODO implement here
    }

}