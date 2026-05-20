package affichage;

import Jeu.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


public class PanelGrille extends JPanel {

    private Monde monde;


    public PanelGrille(Monde monde) {
        this.monde = monde;

        setLayout(new GridLayout(10, 10, 4, 4));
        setBackground(new Color(210, 210, 210));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        rafraichir();
    }


    public void rafraichir() {
        removeAll();

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                Secteur secteur = monde.getGrille()[ligne][colonne];

                JPanel caseSecteur = new JPanel(new GridLayout(2, 1));
                caseSecteur.setPreferredSize(new Dimension(60, 60));
                caseSecteur.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));
                caseSecteur.setBackground(new Color(245, 245, 245));

                JLabel haut = new JLabel("", SwingConstants.CENTER);
                JLabel bas = new JLabel("", SwingConstants.CENTER);

                styliserLabel(haut);
                styliserLabel(bas);

                if (secteur.getEau() != null) {
                    caseSecteur.setLayout(new BorderLayout());

                    ImageIcon icone = new ImageIcon(getClass().getClassLoader().getResource("images/ocean.jpg"));
                    JLabel labelEau = new JLabel(new ImageIcon(icone.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                    labelEau.setHorizontalAlignment(SwingConstants.CENTER);
                    labelEau.setVerticalAlignment(SwingConstants.CENTER);

                    caseSecteur.add(labelEau, BorderLayout.CENTER);

                } else {
                    if (secteur.getMine() != null) {
                        haut.setText("M" + secteur.getMine().getId());
                        haut.setBackground(new Color(255, 204, 102));
                        haut.setForeground(new Color(80, 50, 0));
                    } else if (secteur.getEntrepot() != null) {
                        haut.setText("E" + secteur.getEntrepot().getId());
                        haut.setBackground(new Color(255, 235, 140));
                        haut.setForeground(new Color(90, 70, 0));
                    } else {
                        haut.setBackground(new Color(250, 250, 250));
                    }

                    if (secteur.getRobot() != null) {
                        bas.setText("R" + secteur.getRobot().getId());
                        bas.setBackground(new Color(200, 230, 200));
                        bas.setForeground(new Color(20, 90, 20));
                    } else {
                        bas.setBackground(new Color(250, 250, 250));
                    }
                    caseSecteur.add(haut);
                    caseSecteur.add(bas);
                }

                add(caseSecteur);
            }
        }

        revalidate();
        repaint();
    }


    private void styliserLabel(JLabel label) {
        label.setOpaque(true);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }
}