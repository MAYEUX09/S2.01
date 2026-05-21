package affichage;

import Jeu.*;
import javax.swing.*;
import java.awt.*;

public class PanelGrille extends JPanel {

    private Monde monde;
    private Image imgEau = new ImageIcon(getClass().getClassLoader().getResource("images/ocean.jpg")).getImage();
    private Image imgTerrain = new ImageIcon(getClass().getClassLoader().getResource("images/terrain.jpg")).getImage();
    private Image imgMine = new ImageIcon(getClass().getClassLoader().getResource("images/mine.jpg")).getImage();
    private Image imgEntrepot = new ImageIcon(getClass().getClassLoader().getResource("images/entrepot.jpg")).getImage();
    private Image imgRobotOr = new ImageIcon(getClass().getClassLoader().getResource("images/robotor.jpg")).getImage();
    private Image imgRobotNi = new ImageIcon(getClass().getClassLoader().getResource("images/robotni.jpg")).getImage();

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

                JPanel caseSecteur = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        int w = getWidth();
                        int h = getHeight();

                        g.setFont(new Font("Arial", Font.BOLD, 12));

                        if (secteur.getEau() != null) {
                            g.drawImage(imgEau, 0, 0, w, h, this);
                            return;
                        } else {
                            g.drawImage(imgTerrain, 0, 0, w, h, this);
                        }

                        boolean aRobot = false;
                        if (secteur.getRobot() != null) {
                            aRobot = true;
                        }

                        boolean aBatiment = false;
                        if (secteur.getMine() != null || secteur.getEntrepot() != null) {
                            aBatiment = true;
                        }

                        int hauteurBatiment;
                        int yRobot;
                        int hauteurRobot;

                        if (aRobot == true && aBatiment == true) {
                            hauteurBatiment = h / 2;
                            yRobot = h / 2;
                            hauteurRobot = h / 2;
                        } else {
                            hauteurBatiment = h;
                            yRobot = 0;
                            hauteurRobot = h;
                        }

                        if (secteur.getMine() != null) {
                            g.drawImage(imgMine, 0, 0, w, hauteurBatiment, this);
                            g.setColor(Color.WHITE);
                            g.drawString("M" + secteur.getMine().getId(), 2, 12);
                        } else if (secteur.getEntrepot() != null) {
                            g.drawImage(imgEntrepot, 0, 0, w, hauteurBatiment, this);
                            g.setColor(Color.BLACK);
                            g.drawString("E" + secteur.getEntrepot().getId(), 2, 12);
                        }

                        if (aRobot == true) {
                            Image imageChoisie;
                            if (secteur.getRobot().getType().equalsIgnoreCase("OR")) {
                                imageChoisie = imgRobotOr;
                            } else {
                                imageChoisie = imgRobotNi;
                            }

                            g.drawImage(imageChoisie, 0, yRobot, w, hauteurRobot, this);

                            g.setColor(Color.WHITE);
                            g.drawString("R" + secteur.getRobot().getId(), 2, yRobot + 12);
                        }
                    }
                };

                caseSecteur.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));
                add(caseSecteur);
            }
        }

        revalidate();
        repaint();
    }
}