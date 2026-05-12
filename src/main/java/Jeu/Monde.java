package Jeu;
import java.util.*;

public class Monde {
    private Secteur[][] grille;
    private ArrayList<Robot> lesRobots;
    private int numeroTour;

    public Monde() {
        this.grille = new Secteur[10][10];
        this.lesRobots = new ArrayList<>();
        this.numeroTour = 1;
    }

    public void initialisation() {
        Random generateurAlea = new Random();

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                grille[ligne][colonne] = new Secteur(new Terrain(ligne * 10 + colonne));
            }
        }

// ===============================================
// Génération de l'eau
// ===============================================

        int nbeau = generateurAlea.nextInt(11);
        for (int eauPlacee = 0; eauPlacee < nbeau; ) {
            int ligneAlea = generateurAlea.nextInt(10);
            int colonneAlea = generateurAlea.nextInt(10);
            if (grille[ligneAlea][colonneAlea].getEau() == null) {
                grille[ligneAlea][colonneAlea] = new Secteur(new Eau());
                eauPlacee++;
            }
        }

// ===============================================
// Génération des mines
// ===============================================

        String[] typesMinerais = {"NI", "OR"};
        int idMine = 0;
        int idEntrepot = 1;

        for (String typeActuel : typesMinerais) {
            int nombreMinesAVolonte = generateurAlea.nextInt(2) + 1;

            for (int minesPlacees = 0; minesPlacees < nombreMinesAVolonte; ) {
                int capaciteMines = generateurAlea.nextInt(50, 100);
                int ligneAlea = generateurAlea.nextInt(10);
                int colonneAlea = generateurAlea.nextInt(10);

                if (grille[ligneAlea][colonneAlea].getEau() == null
                        && grille[ligneAlea][colonneAlea].getMine() == null
                        && grille[ligneAlea][colonneAlea].getEntrepot() == null) {

                    grille[ligneAlea][colonneAlea].setMine(
                            new Mine(idMine, typeActuel, capaciteMines, capaciteMines)
                    );

                    idMine++;
                    minesPlacees++;
                }
            }

// ===============================================
// Génération des entrepôts
// ===============================================

            boolean entrepotPlace = false;
            while (!entrepotPlace) {
                int ligneAlea = generateurAlea.nextInt(10);
                int colonneAlea = generateurAlea.nextInt(10);
                if (grille[ligneAlea][colonneAlea].getEau() == null && grille[ligneAlea][colonneAlea].getEntrepot() == null && grille[ligneAlea][colonneAlea].getMine() == null) {
                    grille[ligneAlea][colonneAlea].setEntrepot(new Entrepot(idEntrepot, typeActuel, ligneAlea, colonneAlea));
                    entrepotPlace = true;
                    idEntrepot++ ;
                }

            }
        }

// ===============================================
//  Génération des robots
// ===============================================

        int totalRobots = generateurAlea.nextInt(4) + 2; // entre 2 et 5
        int idRobot = 1;

        boolean robotOrPlace = false;
        while (!robotOrPlace) {
            int ligneAlea = generateurAlea.nextInt(10);
            int colonneAlea = generateurAlea.nextInt(10);

            if (grille[ligneAlea][colonneAlea].getEau() == null
                    && grille[ligneAlea][colonneAlea].getRobot() == null) {

                int capaciteStockage = generateurAlea.nextInt(5) + 5;
                int capaciteExtraction = generateurAlea.nextInt(3) + 1;

                Robot nouveauRobot = new Robot(idRobot, "OR", capaciteStockage, capaciteExtraction, ligneAlea, colonneAlea);
                grille[ligneAlea][colonneAlea].setRobot(nouveauRobot);
                lesRobots.add(nouveauRobot);

                idRobot++;
                robotOrPlace = true;
            }
        }

// 1 robot NI obligatoire
        boolean robotNiPlace = false;
        while (!robotNiPlace) {
            int ligneAlea = generateurAlea.nextInt(10);
            int colonneAlea = generateurAlea.nextInt(10);

            if (grille[ligneAlea][colonneAlea].getEau() == null
                    && grille[ligneAlea][colonneAlea].getRobot() == null) {

                int capaciteStockage = generateurAlea.nextInt(5) + 5;
                int capaciteExtraction = generateurAlea.nextInt(3) + 1;

                Robot nouveauRobot = new Robot(idRobot, "NI", capaciteStockage, capaciteExtraction, ligneAlea, colonneAlea);
                grille[ligneAlea][colonneAlea].setRobot(nouveauRobot);
                lesRobots.add(nouveauRobot);

                idRobot++;
                robotNiPlace = true;
            }
        }

// Robots restants
        for (int robotsPlaces = 2; robotsPlaces < totalRobots; robotsPlaces++) {
            boolean robotPlace = false;

            while (!robotPlace) {
                int ligneAlea = generateurAlea.nextInt(10);
                int colonneAlea = generateurAlea.nextInt(10);

                if (grille[ligneAlea][colonneAlea].getEau() == null
                        && grille[ligneAlea][colonneAlea].getRobot() == null) {

                    int capaciteStockage = generateurAlea.nextInt(5) + 5;
                    int capaciteExtraction = generateurAlea.nextInt(3) + 1;
                    String typeRobot = typesMinerais[generateurAlea.nextInt(2)];

                    Robot nouveauRobot = new Robot(idRobot, typeRobot, capaciteStockage, capaciteExtraction, ligneAlea, colonneAlea);
                    grille[ligneAlea][colonneAlea].setRobot(nouveauRobot);
                    lesRobots.add(nouveauRobot);

                    idRobot++;
                    robotPlace = true;
                }
            }
        }
        }

// ===============================================
//  Génération des robots
// ===============================================
    public void tour() {
        Scanner clavier = new Scanner(System.in);

        for (Robot robotActuel : lesRobots) {
            this.affichermonde();
            System.out.println("Robot " + robotActuel.getId() + " (" + robotActuel.getType() + ") en [" + robotActuel.getLigne() + "," + robotActuel.getColonne() + "] (Stock:" + robotActuel.getStockActuel() + ")");
            System.out.print("Action (nord,sud,est,ouest,recolter,deposer) : ");
            String action = clavier.nextLine().toLowerCase();

            int ancienneLigne = robotActuel.getLigne();
            int ancienneColonne = robotActuel.getColonne();
            int nouvelleLigne = ancienneLigne;
            int nouvelleColonne = ancienneColonne;

            if (action.equals("recolter")) {
                robotActuel.récolter(grille[ancienneLigne][ancienneColonne].getMine());
            } else if (action.equals("deposer")) {
                robotActuel.déposer(grille[ancienneLigne][ancienneColonne].getEntrepot());
            } else {
                if (action.equals("nord")) nouvelleLigne--;
                else if (action.equals("sud")) nouvelleLigne++;
                else if (action.equals("est")) nouvelleColonne++;
                else if (action.equals("ouest")) nouvelleColonne--;

                boolean estDansLimites = (nouvelleLigne >= 0 && nouvelleLigne < 10 && nouvelleColonne >= 0 && nouvelleColonne < 10);

                if (estDansLimites && grille[nouvelleLigne][nouvelleColonne].getEau() == null && grille[nouvelleLigne][nouvelleColonne].getRobot() == null) {
                    grille[ancienneLigne][ancienneColonne].setRobot(null);
                    robotActuel.avancer(action);
                    grille[nouvelleLigne][nouvelleColonne].setRobot(robotActuel);
                } else {
                    System.out.println("Mouvement impossible !");
                }
            }
        }

        numeroTour++;
    }

    public void affichermonde() {
        System.out.print("   ");
        for (int colonne = 0; colonne < 10; colonne++) {
            System.out.print("  " + colonne + "  ");
        }
        System.out.println();

        System.out.print("   ");
        for (int colonne = 0; colonne < 10; colonne++) System.out.print("+----");
        System.out.println("+");

        ArrayList<Mine> recapMines = new ArrayList<>();
        ArrayList<Entrepot> recapEntrepots = new ArrayList<>();

        for (int ligne = 0; ligne < 10; ligne++) {
            System.out.print(" " + ligne + " |");
            for (int colonne = 0; colonne < 10; colonne++) {
                Secteur secteurActuel = grille[ligne][colonne];

                if (secteurActuel.getMine() != null && !recapMines.contains(secteurActuel.getMine())) {
                    recapMines.add(secteurActuel.getMine());
                }
                if (secteurActuel.getEntrepot() != null && !recapEntrepots.contains(secteurActuel.getEntrepot())) {
                    recapEntrepots.add(secteurActuel.getEntrepot());
                }

                if (secteurActuel.getEau() != null) {
                    System.out.print("X X |");
                } else if (secteurActuel.getEntrepot() != null) {
                    System.out.print("E " + secteurActuel.getEntrepot().getId() + " |");
                } else if (secteurActuel.getMine() != null) {
                    System.out.print("M " + secteurActuel.getMine().getId() + " |");
                } else {
                    System.out.print("    |");
                }
            }
            System.out.println();

            System.out.print("   |");
            for (int colonne = 0; colonne < 10; colonne++) {
                Secteur secteurActuel = grille[ligne][colonne];
                if (secteurActuel.getEau() != null) {
                    System.out.print("X X |");
                } else if (secteurActuel.getRobot() != null) {
                    System.out.print("R " + secteurActuel.getRobot().getId() + " |");
                } else {
                    System.out.print("    |");
                }
            }
            System.out.println();

            System.out.print("   ");
            for (int colonne = 0; colonne < 10; colonne++) System.out.print("+----");
            System.out.println("+");
        }

        System.out.println("\nTour " + numeroTour);

        for (int indexMine = 0; indexMine < recapMines.size(); indexMine++) {
            Mine mineActuelle = recapMines.get(indexMine);
            int mineLigne = -1, mineColonne = -1;

            for(int ligne = 0; ligne < 10; ligne++) {
                for(int colonne = 0; colonne < 10; colonne++) {
                    if(grille[ligne][colonne].getMine() == mineActuelle) {
                        mineLigne = ligne;
                        mineColonne = colonne;
                    }
                }
            }
            System.out.printf("| M%-2d %2d %2d   %-2s   %3d / %3d |\n",
                    mineActuelle.getId(), mineLigne, mineColonne, mineActuelle.getTypeMinerai(), mineActuelle.getCapaciteActuel(), mineActuelle.getCapaciteMax());
        }

        for (int indexEntrepot = 0; indexEntrepot < recapEntrepots.size(); indexEntrepot++) {
            Entrepot entrepotActuel = recapEntrepots.get(indexEntrepot);
            int entrepotLigne = -1, entrepotColonne = -1;

            for(int ligne = 0; ligne < 10; ligne++) {
                for(int colonne = 0; colonne < 10; colonne++) {
                    if(grille[ligne][colonne].getEntrepot() == entrepotActuel) {
                        entrepotLigne = ligne;
                        entrepotColonne = colonne;
                    }
                }
            }
            System.out.printf("| E%-2d %2d %2d   %-2s   %3d       |\n",
                    entrepotActuel.getId(), entrepotLigne, entrepotColonne, entrepotActuel.getTypeMinerai(), entrepotActuel.getStockActuel());
        }

        for (Robot robotActuel : lesRobots) {
            System.out.printf("| R%-2d %2d %2d   %-2s   %3d / %3d |\n",
                    robotActuel.getId(), robotActuel.getLigne(), robotActuel.getColonne(), robotActuel.getType(), robotActuel.getStockActuel(), robotActuel.getStockage());
        }
        System.out.println();
    }
}