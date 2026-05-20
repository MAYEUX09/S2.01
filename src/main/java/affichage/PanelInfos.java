package affichage;

import Jeu.*;
import Jeu.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PanelInfos extends JPanel {

    private Monde monde;
    private JTextArea texte;
    private ArrayList<Mine> lesmines;
    private ArrayList<Entrepot> lesentrepots;


    public PanelInfos(Monde monde) {
        this.monde = monde;
        setLayout(new BorderLayout());

        texte = new JTextArea(30, 25);
        texte.setEditable(false);

        add(new JScrollPane(texte), BorderLayout.CENTER);
        rafraichir();
    }

    public void rafraichir() {
        StringBuilder sb = new StringBuilder();

        sb.append("Tour : ").append(monde.getNumeroTour()).append("\n\n");

        sb.append("MINES\n");
        if (monde.getlesmines() != null) {
            for (Mine mine : monde.getlesmines()) {
                int mineLigne = -1;
                int mineColonne = -1;

                Secteur[][] grille = monde.getGrille();
                for (int l = 0; l < 10; l++) {
                    for (int c = 0; c < 10; c++) {
                        if (grille[l][c].getMine() == mine) {
                            mineLigne = l;
                            mineColonne = c;
                        }
                    }
                }

                sb.append("M").append(mine.getId())
                        .append(" [").append(mineLigne).append(",").append(mineColonne).append("] ")
                        .append(mine.getTypeMinerai()).append(" : ")
                        .append(mine.getCapaciteActuel()).append(" / ").append(mine.getCapaciteMax())
                        .append("\n");
            }
        }
        sb.append("\n");
        sb.append("ENTREPOTS\n");
        if (monde.getlesentrepot() != null) {
            for (Entrepot entrepot : monde.getlesentrepot()) {
                int entrepotLigne = -1;
                int entrepotColonne = -1;

                Secteur[][] grille = monde.getGrille();
                for (int l = 0; l < 10; l++) {
                    for (int c = 0; c < 10; c++) {
                        if (grille[l][c].getEntrepot() == entrepot) {
                            entrepotLigne = l;
                            entrepotColonne = c;
                        }
                    }
                }
                sb.append("E").append(entrepot.getId())
                        .append(" [").append(entrepotLigne).append(",").append(entrepotColonne).append("] ")
                        .append(entrepot.getTypeMinerai()).append(" : ")
                        .append(entrepot.getStockActuel())
                        .append("\n");
            }
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("ROBOTS\n");
        for (Robot robot : monde.getLesRobots()) {
            sb.append("R").append(robot.getId())
                    .append(" [").append(robot.getLigne()).append(",").append(robot.getColonne()).append("]")
                    .append(" ").append(robot.getType())
                    .append(" Stock: ").append(robot.getStockActuel())
                    .append(" / ").append(robot.getStockage())
                    .append("\n");
        }

        texte.setText(sb.toString());
    }
}