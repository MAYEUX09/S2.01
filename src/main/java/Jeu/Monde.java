package Jeu;

import java.util.ArrayList;
import java.util.Random;

public class Monde {
    private Secteur[][] grille;
    private ArrayList<Robot> lesRobots;
    private ArrayList<Secteur> lesSecteurs;

    public Monde() {
        this.grille = new Secteur[10][10];
        this.lesRobots = new ArrayList<>();
        this.lesSecteurs = new ArrayList<>();
    }

    public void initialisation() {
        for (int i = 0; i < this.grille.length; i++) {
            for (int j = 0; j < this.grille[i].length; j++) {
                int id = i * 10 + j;
                // vérifier constructeurs Secteur et Terrain avec l'équipe
                // Création de grille de 100 terrains
                grille[i][j] = new Secteur(new Terrain(id)); // Partie Ambiguïe et non complète en attente de terrain
            }
        }
        Random rand = new Random();
        // Ajout de 10 plan d'eau à des endroits aléatoire de la case
        int eauPlacee = 0;
        while (eauPlacee < 10){
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);
            if (grille[ligne][colonne].getEau() == null){
                grille[ligne][colonne] = new Secteur(new Eau());
                eauPlacee++;
            }
        }

        // Ajout de mines aléatoirement
        int nbMinesNI = rand.nextInt(2) + 1; // 1 ou 2
        int nbMinesOR = rand.nextInt(2) + 1; // 1 ou 2
        int idMine = 1;
        int minesplacees = 0;
        int mineORsplacees = 0;
        // Ajout de mines Nickel
        while (minesplacees < nbMinesNI) {
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);
            // On vérifie qu'il n'y a pas déjà de mine
            if (grille[ligne][colonne].getMine() == null && grille[ligne][colonne].getEau() == null && grille[ligne][colonne].getEntrepot() == null) {
                // On calcule la capacité
                int capacite = rand.nextInt(51) + 50;
                // On crée et place la mine
                Mine mine = new Mine(idMine, "NI", capacite, capacite);
                grille[ligne][colonne].setMine(mine);
                // On incrémente
                minesplacees++;
                idMine++;
            }
        }
        // Ajout de mines OR
        while (mineORsplacees < nbMinesOR) {
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);
            // On vérifie qu'il n'y a pas déjà de mine
            if (grille[ligne][colonne].getMine() == null && grille[ligne][colonne].getEau() == null && grille[ligne][colonne].getEntrepot() == null) {
                // On calcule la capacité
                int capacite = rand.nextInt(51) + 50;
                // On crée et place la mine
                Mine mine = new Mine(idMine, "OR", capacite, capacite);
                grille[ligne][colonne].setMine(mine);
                // On incrémente
                mineORsplacees++;
                idMine++;
            }
    }


    public void tour() {
        // TODO implement here
    }

    /**
     *
     */
    public void affichermonde() {
        // TODO implement here
    }

}