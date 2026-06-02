package Jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.List;

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

    public String modeDuRobot() {
        if (this.quantiteStockeeActuellement < this.capaciteStockageMaximale) {
            return "Mine";
        } else {
            return "Entrepot";
        }
    }

    public String[][] generergrille(Secteur[][] grille) {
        String[][] grilletempo = new String[10][10];
        for (int i = 0; i < grilletempo.length; i++) {
            for (int j = 0; j < grilletempo[i].length; j++) {
                if (grille[i][j].getEau() != null) {
                    grilletempo[i][j] = "XX";
                } else if (grille[i][j].getEntrepot() != null && grille[i][j].getEntrepot().getTypeMinerai().equals("NI")) {
                    grilletempo[i][j] = "eNI";
                } else if (grille[i][j].getEntrepot() != null && grille[i][j].getEntrepot().getTypeMinerai().equals("OR")) {
                    grilletempo[i][j] = "eOR";
                } else if (grille[i][j].getRobot() != null && grille[i][j].getRobot().getType().equals("OR")) {
                    grilletempo[i][j] = "rOR";
                } else if (grille[i][j].getRobot() != null && grille[i][j].getRobot().getType().equals("NI")) {
                    grilletempo[i][j] = "rNI";
                } else {
                    grilletempo[i][j] = " . ";
                }
            }
        }
        return grilletempo;
    }

    public String executerDijkstra(Secteur[][] grille, int targetLigne, int targetColonne) {
        Map<Point, Integer> dist = new HashMap<>();
        Map<Point, Point> prev = new HashMap<>();
        PriorityQueue<Point> Q = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        int taille = 10;

        for (int l = 0; l < taille; l++) {
            for (int c = 0; c < taille; c++) {
                Point p = new Point(l, c);
                dist.put(p, Integer.MAX_VALUE);
                prev.put(p, null);
            }
        }

        Point source = new Point(this.positionLigne, this.positionColonne);
        Point target = new Point(targetLigne, targetColonne);

        dist.put(source, 0);
        Q.add(source);

        while (!Q.isEmpty()) {
            Point u = Q.poll();

            if (u.equals(target)) break;

            int[][] directions = {{u.ligne - 1, u.colonne}, {u.ligne + 1, u.colonne}, {u.ligne, u.colonne + 1}, {u.ligne, u.colonne - 1}};

            for (int[] dir : directions) {
                int vl = dir[0];
                int vc = dir[1];

                if (vl >= 0 && vl < taille && vc >= 0 && vc < taille) {
                    Point v = new Point(vl, vc);

                    boolean isObstacle = grille[vl][vc].getEau() != null;
                    if (grille[vl][vc].getRobot() != null && !v.equals(target)) {
                        isObstacle = true;
                    }

                    if (!isObstacle) {
                        int alt = dist.get(u) + 1;

                        if (alt < dist.get(v)) {
                            Q.remove(v);

                            dist.put(v, alt);
                            prev.put(v, u);

                            Q.add(v);
                        }
                    }
                }
            }
        }

        if (prev.get(target) == null) return null;

        List<String> cheminComplet = new ArrayList<>();
        Point courant = target;

        while (!courant.equals(source)) {
            cheminComplet.add("(" + courant.colonne + "," + courant.ligne + ")");
            courant = prev.get(courant);
        }

        Collections.reverse(cheminComplet);

        System.out.println("   [Chemin Robot " + this.identifiant + "] : (" + this.positionColonne + "," + this.positionLigne + ") -> " + String.join(" -> ", cheminComplet));
        Point premiereEtape = target;
        while (!prev.get(premiereEtape).equals(source)) {
            premiereEtape = prev.get(premiereEtape);
        }

        if (premiereEtape.ligne < source.ligne) return "nord";
        if (premiereEtape.ligne > source.ligne) return "sud";
        if (premiereEtape.colonne > source.colonne) return "est";
        if (premiereEtape.colonne < source.colonne) return "ouest";

        return null;
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

    public int getId() { return identifiant; }
    public String getType() { return typeDeSpecialisation; }
    public int getLigne() { return positionLigne; }
    public int getColonne() { return positionColonne; }
    public int getStockActuel() { return quantiteStockeeActuellement; }
    public int getStockage() { return capaciteStockageMaximale; }
    public int getCredit(){ return credit; }
    public void resetcredit(){ this.credit = 1; }

    @Override
    public String toString() {
        return "Robot " + this.identifiant + " Spé : " + this.typeDeSpecialisation;
    }

    private static class Point {
        int ligne;
        int colonne;

        public Point(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return ligne == point.ligne && colonne == point.colonne;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ligne, colonne);
        }
    }
}