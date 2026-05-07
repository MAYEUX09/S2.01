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
