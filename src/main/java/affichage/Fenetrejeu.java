package affichage;

import Jeu.*;
import Jeu.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Fenetrejeu extends JFrame {
    private Monde monMonteurdeJeu;
    private JPanel panneauGrille;
    private JLabel jbltour;
    private JLabel lblInfosRobot;
    private int compteurtour = 0;

    public Fenetrejeu(Monde monde) {
        this.monMonteurdeJeu = monde;

        this.setTitle("Simulation de minage");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BorderLayout());

        JLabel texte = new JLabel("Bienvenue dans le jeu", SwingConstants.CENTER);
        texte.setFont(new Font("Arial", Font.BOLD, 16));
        panneauPrincipal.add(texte, BorderLayout.NORTH);

        this.panneauGrille = new JPanel();
        this.panneauGrille.setLayout(new GridLayout(10, 10));
        panneauPrincipal.add(this.panneauGrille, BorderLayout.CENTER);

        JPanel panneauLateral = new JPanel();
        panneauLateral.setLayout(new BoxLayout(panneauLateral, BoxLayout.Y_AXIS));
        panneauLateral.setPreferredSize(new Dimension(250, 600));
        panneauLateral.setBorder(BorderFactory.createTitledBorder("Contrôle & Infos"));
        panneauLateral.setBackground(new Color(245, 245, 245));

        jbltour = new JLabel("Tour actuel : 0");
        jbltour.setFont(new Font("Arial", Font.BOLD, 14));
        jbltour.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauLateral.add(jbltour);

        panneauLateral.add(Box.createVerticalStrut(30));

        JLabel lblTitreRobot = new JLabel("Robot sélectionné :");
        lblTitreRobot.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitreRobot.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauLateral.add(lblTitreRobot);

        lblInfosRobot = new JLabel("Aucun (Cliquez sur un R)");
        lblInfosRobot.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInfosRobot.setForeground(Color.BLUE);
        lblInfosRobot.setAlignmentX(Component.LEFT_ALIGNMENT);
        panneauLateral.add(lblInfosRobot);

        panneauPrincipal.add(panneauLateral, BorderLayout.EAST);

        JButton boutonSuivant = new JButton("Tour Suivant");
        boutonSuivant.setFont(new Font("Arial", Font.BOLD, 14));
        boutonSuivant.setPreferredSize(new Dimension(0, 45));
        boutonSuivant.addActionListener(e -> {
            monMonteurdeJeu.tour();
            compteurtour++;
            rafraichirGrille();
        });
        panneauPrincipal.add(boutonSuivant, BorderLayout.SOUTH);

        this.setContentPane(panneauPrincipal);

        rafraichirGrille();
    }

    public void rafraichirGrille() {
        if (jbltour != null) {
            jbltour.setText("Tour actuel : " + compteurtour);
        }

        panneauGrille.removeAll();

        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                final Secteur secteuractuel = monMonteurdeJeu.getGrille()[ligne][colonne];

                JPanel caseSecteur = new JPanel();
                caseSecteur.setLayout(new GridLayout(2, 2));
                caseSecteur.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel zoneHautGauche = new JLabel("", SwingConstants.CENTER);
                zoneHautGauche.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel zoneHautDroite = new JLabel("", SwingConstants.CENTER);
                zoneHautDroite.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel zoneBasGauche = new JLabel("", SwingConstants.CENTER);
                zoneBasGauche.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel zoneBasDroite = new JLabel("", SwingConstants.CENTER);
                zoneBasDroite.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (secteuractuel.getEau() != null) {
                    caseSecteur.setBackground(Color.BLUE);
                    zoneHautGauche.setText("X"); zoneHautDroite.setText("X");
                    zoneBasGauche.setText("X"); zoneBasDroite.setText("X");

                    zoneHautGauche.setForeground(Color.WHITE); zoneHautDroite.setForeground(Color.WHITE);
                    zoneBasGauche.setForeground(Color.WHITE); zoneBasDroite.setForeground(Color.WHITE);
                } else {
                    caseSecteur.setBackground(Color.WHITE);
                    zoneHautGauche.setForeground(Color.BLACK); zoneHautDroite.setForeground(Color.BLACK);
                    zoneBasGauche.setForeground(Color.BLACK); zoneBasDroite.setForeground(Color.BLACK);
                }

                if (secteuractuel.getEntrepot() != null) {
                    zoneHautGauche.setText("E");
                    zoneHautDroite.setText(String.valueOf(secteuractuel.getEntrepot().getId()));

                    if (secteuractuel.getEntrepot().getTypeMinerai().equals("OR")) {
                        caseSecteur.setBackground(Color.orange);
                        zoneHautGauche.setForeground(Color.WHITE); zoneHautDroite.setForeground(Color.WHITE);
                    }
                    else if (secteuractuel.getEntrepot().getTypeMinerai().equals("NI")) {
                        caseSecteur.setBackground(Color.GRAY);
                        zoneHautGauche.setForeground(Color.WHITE); zoneHautDroite.setForeground(Color.WHITE);

                    }
                }

                if (secteuractuel.getMine() != null) {
                    zoneHautGauche.setText("M");
                    zoneHautDroite.setText(String.valueOf(secteuractuel.getMine().getId()));

                    if (secteuractuel.getMine().getTypeMinerai().equals("NI")) {
                        caseSecteur.setBackground(Color.GRAY);
                        zoneHautGauche.setForeground(Color.WHITE); zoneHautDroite.setForeground(Color.WHITE);
                    }
                    else if (secteuractuel.getMine().getTypeMinerai().equals("OR")) {
                        caseSecteur.setBackground(Color.YELLOW);
                    }
                }

                if (secteuractuel.getRobot() != null) {
                    zoneBasGauche.setText("R");
                    zoneBasDroite.setText(String.valueOf(secteuractuel.getRobot().getId()));
                    caseSecteur.setBackground(Color.RED);
                    if (secteuractuel.getEau() != null || (secteuractuel.getMine() != null && secteuractuel.getMine().getTypeMinerai().equals("NI"))) {
                        zoneBasGauche.setForeground(Color.WHITE); zoneBasDroite.setForeground(Color.WHITE);
                    }

                    MouseAdapter ecouteurRobot = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (secteuractuel.getRobot() != null) {
                                Robot r = secteuractuel.getRobot();
                                lblInfosRobot.setText("ID : " + r.getId() + " (Ligne: " + r.getLigne() + ", Col: " + r.getColonne() + ")");
                            }
                        }
                    };

                    zoneBasGauche.addMouseListener(ecouteurRobot);
                    zoneBasDroite.addMouseListener(ecouteurRobot);

                    zoneBasGauche.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    zoneBasDroite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                caseSecteur.add(zoneHautGauche); caseSecteur.add(zoneHautDroite);
                caseSecteur.add(zoneBasGauche); caseSecteur.add(zoneBasDroite);
                panneauGrille.add(caseSecteur);
            }
        }

        panneauGrille.revalidate();
        panneauGrille.repaint();
    }
}