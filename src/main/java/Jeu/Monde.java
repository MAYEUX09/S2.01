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
        // Ajout de 10 plan d'eau à des endroits aléatoire de la case
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int colonne = rand.nextInt(10); // nombre aléatoire entre 0 et 9
            int ligne = rand.nextInt(10);// nombre aléatoire entre 0 et 9
            grille[ligne][colonne] = new Secteur(new Eau(ligne * 10 + colonne)); // Partie Ambiguïe et non complète en attente de Eau
        }
        // Ajout de mine aléatoirement
        int nbMinesNI = rand.nextInt(2) + 1; // 1 ou 2
        int nbMinesOR = rand.nextInt(2) + 1; // 1 ou 2
        int idMine = 1;

        // Mines Nickel
        for (int i = 0; i < nbMinesNI; i++) {
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);
            if (grille[ligne][colonne].getMine() == null) { // Vérifier avec l'équipe
                int capacite = rand.nextInt(51) + 50; // entre 50 et 100
                Mine mine = new Mine(idMine, "NI", capacite, capacite);
                grille[ligne][colonne].setMine(mine); // vérifier avec l'équipe
                idMine++;
            }
        }

        // Mines OR
        for (int i = 0; i < nbMinesOR; i++) {
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);
            if (grille[ligne][colonne].getMine() == null) { // Vérifier avec l'équipe
                int capacite = rand.nextInt(51) + 50; // entre 50 et 100
                Mine mine = new Mine(idMine, "OR", capacite, capacite);
                grille[ligne][colonne].setMine(mine); // vérifier avec l'équipe
                idMine++;
            }
        }

        // Entrepot
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