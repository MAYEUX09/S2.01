package affichage;

import Jeu.*;
import Jeu.Robot;

import javax.swing.*;
import java.awt.*;

public class PanelInfos extends JPanel {

    private Monde monde;
    private JTextArea texte;

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

        sb.append("ROBOTS\n");
        for (Robot robot : monde.getLesRobots()) {
            sb.append("R").append(robot.getId())
                    .append(" ").append(robot.getLigne())
                    .append(" ").append(robot.getColonne())
                    .append(" ").append(robot.getType())
                    .append(" ").append(robot.getStockActuel())
                    .append(" / ").append(robot.getStockage())
                    .append("\n");
        }

        texte.setText(sb.toString());
    }
}