package Jeu;
import java.util.*;

public class Monde {
    private Secteur[][] grille;
    private ArrayList<Robot> lesRobots;
    private int numeroTour;
    private ArrayList<Mine> lesMines;
    private ArrayList<Entrepot> lesentrepot;

    public Monde() {
        this.grille = new Secteur[10][10];
        this.lesRobots = new ArrayList<>();
        this.numeroTour = 1;
        this.lesMines = new ArrayList<>();
        this.lesentrepot = new ArrayList<>();
    }

    public void initialisation() {
        Random generateurAlea = new Random();

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                grille[ligne][colonne] = new Secteur(new Terrain(ligne * 10 + colonne));
            }
        }
        int nbEau = generateurAlea.nextInt(11)+1;
        for (int eauPlacee = 0; eauPlacee < nbEau; ) {
            int ligneAlea = generateurAlea.nextInt(10);
            int colonneAlea = generateurAlea.nextInt(10);
            if (grille[ligneAlea][colonneAlea].getEau() == null) {
                grille[ligneAlea][colonneAlea] = new Secteur(new Eau());
                eauPlacee++;
            }
        }

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

                    Mine nouvelleMine = new Mine(idMine, typeActuel, capaciteMines, capaciteMines);

                    grille[ligneAlea][colonneAlea].setMine(nouvelleMine);

                    lesMines.add(nouvelleMine);

                    idMine++;
                    minesPlacees++;
                }
            }

            boolean entrepotPlace = false;
            while (!entrepotPlace) {
                int ligneAlea = generateurAlea.nextInt(10);
                int colonneAlea = generateurAlea.nextInt(10);

                if (grille[ligneAlea][colonneAlea].getEau() == null
                        && grille[ligneAlea][colonneAlea].getEntrepot() == null
                        && grille[ligneAlea][colonneAlea].getMine() == null) {

                    Entrepot nouvelEntrepot = new Entrepot(idEntrepot, typeActuel, ligneAlea, colonneAlea);

                    grille[ligneAlea][colonneAlea].setEntrepot(nouvelEntrepot);
                    lesentrepot.add(nouvelEntrepot);

                    entrepotPlace = true;
                    idEntrepot++;
                }
            }
        }

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


    public void tour() {
        Scanner clavier = new Scanner(System.in);

        for (Robot robotActuel : lesRobots) {
            this.affichermonde();
            System.out.println("Robot " + robotActuel.getId() + " (" + robotActuel.getType() + ") en [" + robotActuel.getLigne() + "," + robotActuel.getColonne() + "] (Stock:" + robotActuel.getStockActuel() + "/" + robotActuel.getStockage() + ")");

            System.out.print("Action (nord,sud,est,ouest,recolter,deposer,auto) : ");
            String action = clavier.nextLine().toLowerCase().trim();

            int ancienneLigne = robotActuel.getLigne();
            int ancienneColonne = robotActuel.getColonne();

            if (action.equals("auto")) {
                Secteur sec = grille[ancienneLigne][ancienneColonne];

                String modeActuel = robotActuel.modeDuRobot(); // "Mine" ou "Entrepot"
                String specialisation = robotActuel.getType(); // "NI" ou "OR"

                if (modeActuel.equals("Entrepot") && sec.getEntrepot() != null
                        && sec.getEntrepot().getTypeMinerai().equals(specialisation)) {
                    action = "deposer";
                } else if (modeActuel.equals("Mine") && sec.getMine() != null
                        && sec.getMine().getTypeMinerai().equals(specialisation)
                        && sec.getMine().getCapaciteActuel() > 0) {
                    action = "recolter";
                } else {

                    int targetL = -1, targetC = -1;

                    if (modeActuel.equals("Entrepot")) {
                        for (int l = 0; l < 10; l++) {
                            for (int c = 0; c < 10; c++) {
                                Entrepot e = grille[l][c].getEntrepot();
                                if (e != null && e.getTypeMinerai().equals(specialisation)) {
                                    targetL = l;
                                    targetC = c;
                                    break;
                                }
                            }
                            if (targetL != -1) break;
                        }
                    } else if (modeActuel.equals("Mine")) {
                        for (int l = 0; l < 10; l++) {
                            for (int c = 0; c < 10; c++) {
                                Mine m = grille[l][c].getMine();
                                if (m != null && m.getTypeMinerai().equals(specialisation) && m.getCapaciteActuel() > 0) {
                                    targetL = l;
                                    targetC = c;
                                    break;
                                }
                            }
                            if (targetL != -1) break;
                        }
                    }

                    if (targetL != -1) {
                        String dir = robotActuel.executerDijkstra(grille, targetL, targetC);
                        if (dir != null) {
                            action = dir;
                            System.out.println("-> Le robot commence son trajet vers le " + action);
                        } else {
                            System.out.println("-> [Dijkstra] Impossible de trouver un chemin !");
                            continue;
                        }
                    } else {
                        System.out.println("-> Aucun objectif valide trouvé pour le robot.");
                        continue;
                    }
                }
            }

            int nouvelleLigne = ancienneLigne;
            int nouvelleColonne = ancienneColonne;

            if (action.equals("recolter")) {
                robotActuel.recolter(grille[ancienneLigne][ancienneColonne].getMine());
            } else if (action.equals("deposer")) {
                robotActuel.deposer(grille[ancienneLigne][ancienneColonne].getEntrepot());
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
                    System.out.println("-> /!\\ Action impossible : un obstacle (Eau ou Robot) bloque la case !");
                }
            }
        }

        numeroTour++;
    }

    public ArrayList<Robot> getLesRobots() {
        return lesRobots;
    }

    public ArrayList<Mine> getlesmines(){
        return lesMines;
    }
    public ArrayList<Entrepot> getlesentrepot(){
        return lesentrepot;
    }
    public int getNumeroTour() {
        return numeroTour;
    }

    public void incrementerTour() {
        numeroTour++;
    }

    public Secteur[][] getGrille() {
        return this.grille;
    }

    public void setGrille(Secteur[][] grille) {
        this.grille = grille;
    }

    public void setLesRobots(ArrayList<Robot> lesRobots) {
        this.lesRobots = lesRobots;
    }

    public void setNumeroTour(int numeroTour) {
        this.numeroTour = numeroTour;
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