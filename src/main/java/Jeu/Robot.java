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

    public void setStockageactuel(int stockageactuel) {
        this.stockageactuel = stockageactuel;
    }

    public void avancer(String direction) {
        if (direction.equals("nord")) {
            this.ligne = this.ligne - 1;
        } else if (direction.equals("sud")) {
            this.ligne = this.ligne + 1;
        } else if (direction.equals("est")) {
            this.colonne = this.colonne + 1;
        } else if (direction.equals("ouest")) {
            this.colonne = this.colonne - 1;
        }
    }

    public int Recolter() {
        Secteur secteurActuel = monde.getGrille()[ligne][colonne];

        if (secteurActuel.getMine() == null) {
            return 0;
        }

        Mine mine = secteurActuel.getMine();

        if (!Minerais.getType().equals(this.Specialite)) {
            return 0;
        }

        if (mine.getStockageactuel() <= 0) {
            return 0;
        }

        int placeDisponible = this.Stockage - this.stockageactuel;

        if (placeDisponible <= 0) {
            return 0;
        }

        int quantiteARecolter = Math.min(this.Capaciteex, placeDisponible);
        int quantiteRecuperee = mine.extraire(quantiteARecolter);

        this.stockageactuel += quantiteRecuperee;

        return quantiteRecuperee;
    }

    public int Deposer() {
        Secteur secteurActuel = monde.getGrille()[ligne][colonne];

        if (secteurActuel.getEntrepot() == null) {
            return 0;
        }

        Entrepot entrepot = secteurActuel.getEntrepot();

        if (!entrepot.getType().equals(this.Specialite)) {
            return 0;
        }

        if (this.stockageactuel <= 0) {
            return 0;
        }

        int quantiteDeposee = this.stockageactuel;
        entrepot.Deposer(quantiteDeposee);
        this.stockageactuel = 0;

        return quantiteDeposee;
    }
}