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

                JPanel caseSecteur = new JPanel(new BorderLayout());
                caseSecteur.setPreferredSize(new Dimension(60, 60));
                caseSecteur.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));
                caseSecteur.setBackground(new Color(245, 245, 245));

                if (secteur.getEau() != null) {

                    caseSecteur.add(creerLabelImage("images/ocean.jpg"), BorderLayout.CENTER);

                } else if (secteur.getMine() == null && secteur.getEntrepot() == null && secteur.getRobot() == null) {

                    caseSecteur.add(creerLabelImage("images/terrain.jpg"), BorderLayout.CENTER);

                } else {

                    JLayeredPane calque = new JLayeredPane();
                    calque.setPreferredSize(new Dimension(60, 60));

                    if (secteur.getMine() != null) {
                        calque.add(creerLabelImageBounds("images/mine.jpg", 80, 80), JLayeredPane.DEFAULT_LAYER);
                        calque.add(creerLabelId("M" + secteur.getMine().getId(), Color.WHITE), JLayeredPane.PALETTE_LAYER);

                    } else if (secteur.getEntrepot() != null) {
                        calque.add(creerLabelImageBounds("images/entrepot.jpg", 80, 80), JLayeredPane.DEFAULT_LAYER);
                        calque.add(creerLabelId("E" + secteur.getEntrepot().getId(), Color.BLACK), JLayeredPane.PALETTE_LAYER);

                    } else {
                        calque.add(creerLabelImageBounds("images/terrain.jpg", 60, 60), JLayeredPane.DEFAULT_LAYER);
                    }

                    if (secteur.getRobot() != null) {
                        calque.add(creerLabelImageBounds("images/robot.jpg", 80, 70), JLayeredPane.MODAL_LAYER);
                        calque.add(creerLabelId("R" + secteur.getRobot().getId(), Color.BLACK), JLayeredPane.POPUP_LAYER);
                    }

                    caseSecteur.add(calque, BorderLayout.CENTER);
                }

                add(caseSecteur);
            }
        }

        revalidate();
        repaint();
    }


    private JLabel creerLabelImage(String chemin) {
        ImageIcon icone = new ImageIcon(getClass().getClassLoader().getResource(chemin));
        JLabel label = new JLabel(new ImageIcon(icone.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel creerLabelImageBounds(String chemin, int largeur, int hauteur) {
        ImageIcon icone = new ImageIcon(getClass().getClassLoader().getResource(chemin));
        JLabel label = new JLabel(new ImageIcon(icone.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH)));
        label.setBounds(0, 0, largeur, hauteur);
        return label;
    }

    private JLabel creerLabelId(String texte, Color couleur) {
        JLabel label = new JLabel(texte);
        label.setBounds(2, 2, 40, 20);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(couleur);
        return label;
    }


    private void styliserLabel(JLabel label) {
        label.setOpaque(true);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }
}