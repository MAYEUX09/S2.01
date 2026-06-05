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

    public String executerDijkstra(Secteur[][] grille, int targetLigne, int targetColonne) {
        if (positionLigne == targetLigne && positionColonne == targetColonne) return null;

        Map<Point, Point> prev = new HashMap<>();
        Map<Point, Integer> dist = new HashMap<>();
        PriorityQueue<PointDistance> file = new PriorityQueue<>(Comparator.comparingInt(p -> p.distance));

        Point source = new Point(positionLigne, positionColonne), cible = new Point(targetLigne, targetColonne);

        file.add(new PointDistance(source, 0));
        prev.put(source, null);
        dist.put(source, 0);

        // 1. Recherche du chemin
        while (!file.isEmpty()) {
            PointDistance courant = file.poll();
            Point u = courant.point;

            if (u.equals(cible)) break;

            int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}}; // Nord, Sud, Est, Ouest
            for (int[] d : directions) {
                Point v = new Point(u.ligne + d[0], u.colonne + d[1]);

                // Vérification : limites de la grille et sans obstacle
                if (v.ligne >= 0 && v.ligne < 10 && v.colonne >= 0 && v.colonne < 10) {
                    if (grille[v.ligne][v.colonne].getEau() == null
                            && (grille[v.ligne][v.colonne].getRobot() == null || v.equals(cible))) {

                        int nouveauCout = dist.get(u) + 1;

                        if (!dist.containsKey(v) || nouveauCout < dist.get(v)) {
                            dist.put(v, nouveauCout);
                            prev.put(v, u);
                            file.add(new PointDistance(v, nouveauCout));
                        }
                    }
                }
            }
        }

        if (!prev.containsKey(cible)) return null; // Aucun chemin possible

        // 2. Remontée du chemin (Affichage + Direction)
        List<String> chemin = new ArrayList<>();
        Point etape = cible;

        while (!prev.get(etape).equals(source)) {
            chemin.add("(" + etape.ligne + "," + etape.colonne + ")");
            etape = prev.get(etape);
        }
        chemin.add("(" + etape.ligne + "," + etape.colonne + ")");
        Collections.reverse(chemin);

        System.out.println("   [Chemin Robot " + this.identifiant + "] : (" + source.ligne + "," + source.colonne + ") -> " + String.join(" -> ", chemin));

        // 3. Retour de la direction
        if (etape.ligne < source.ligne) {
            return "nord";
        } else if (etape.ligne > source.ligne) {
            return "sud";
        } else if (etape.colonne > source.colonne) {
            return "est";
        } else {
            return "ouest";
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


    private static class PointDistance {
        Point point;
        int distance;

        PointDistance(Point point, int distance) {
            this.point = point;
            this.distance = distance;
        }
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