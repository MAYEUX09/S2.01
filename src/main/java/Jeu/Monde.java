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

            // Ajout de l'entrepôt d'Or
            boolean entrepotOrPlace = false;
            while (!entrepotOrPlace) {
                int ligne = rand.nextInt(10);
                int colonne = rand.nextInt(10);

                // On vérifie qu'il n'y a ni mine, ni eau, ni un autre entrepôt
                if (grille[ligne][colonne].getMine() == null && grille[ligne][colonne].getEau() == null && grille[ligne][colonne].getEntrepot() == null) {
                    Entrepot entrepotOr = new Entrepot(1, "OR", ligne, colonne);
                    grille[ligne][colonne].setEntrepot(entrepotOr);
                    entrepotOrPlace = true;
                }
            }

            // Ajout de l'entrepôt de Nickel
            boolean entrepotNiPlace = false;
            while (!entrepotNiPlace) {
                int ligne = rand.nextInt(10);
                int colonne = rand.nextInt(10);

                if (grille[ligne][colonne].getMine() == null && grille[ligne][colonne].getEau() == null && grille[ligne][colonne].getEntrepot() == null) {
                    Entrepot entrepotNi = new Entrepot(2, "NI", ligne, colonne);
                    grille[ligne][colonne].setEntrepot(entrepotNi);
                    entrepotNiPlace = true;
                }
            }
        // Ajout des robots de type Nickel (NI)
        int nbRobotsNI = rand.nextInt(5) + 1; // Entre 1 et 5 robots
        int robotsNiPlaces = 0;
        int idRobot = 1; // On initialise l'ID unique

        while (robotsNiPlaces < nbRobotsNI) {
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);

            if (grille[ligne][colonne].getEau() == null && grille[ligne][colonne].getRobot() == null) {
                // Calcul des capacités aléatoires dictées par le sujet
                int capStockage = rand.nextInt(5) + 5; // Entre 5 et 9
                int capExtraction = rand.nextInt(3) + 1; // Entre 1 et 3

                // Création et placement
                Robot robot = new Robot(idRobot, "NI", capStockage, capExtraction, ligne, colonne);
                grille[ligne][colonne].setRobot(robot);
                lesRobots.add(robot); // On l'ajoute à l'inventaire du monde

                robotsNiPlaces++;
                idRobot++;
            }
        }
        // Ajout des robots de type Or (OR)
        int nbrobotor = rand.nextInt(5) + 1; // Entre 1 et 5 robots
        int robotsORPlaces = 0;

        while (robotsORPlaces < nbrobotor) {
            int ligne = rand.nextInt(10);
            int colonne = rand.nextInt(10);

            if (grille[ligne][colonne].getEau() == null && grille[ligne][colonne].getRobot() == null) {
                // Calcul des capacités aléatoires
                int capStockage = rand.nextInt(5) + 5; // Entre 5 et 9
                int capExtraction = rand.nextInt(3) + 1; // Entre 1 et 3

                // Création et placement
                Robot robot = new Robot(idRobot, "OR", capStockage, capExtraction, ligne, colonne);
                grille[ligne][colonne].setRobot(robot);
                lesRobots.add(robot);

                robotsORPlaces++;
                idRobot++;
            }
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