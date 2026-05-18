package affichage;

import Jeu.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class fenetredejeu2 extends JFrame {

    private Monde monde;
    private PanelGrille panelGrille;
    private PanelInfos panelInfos;
    private PanelCommandes panelCommandes;
    private JLabel labelTitre;
    private JLabel labelTour;

    public fenetredejeu2(Monde monde) {
        this.monde = monde;

        setTitle("Simulation robots mineurs");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panneauPrincipal = new JPanel(new BorderLayout(10, 10));
        panneauPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panneauPrincipal.setBackground(new Color(230, 230, 230));

        JPanel panneauHaut = creerPanneauHaut();
        panelGrille = new PanelGrille(monde);
        panelInfos = new PanelInfos(monde);
        panelCommandes = new PanelCommandes(monde, this);

        JPanel conteneurCentre = new JPanel(new BorderLayout(10, 10));
        conteneurCentre.setOpaque(false);
        conteneurCentre.add(panelGrille, BorderLayout.CENTER);
        conteneurCentre.add(panelInfos, BorderLayout.EAST);

        panneauPrincipal.add(panneauHaut, BorderLayout.NORTH);
        panneauPrincipal.add(conteneurCentre, BorderLayout.CENTER);
        panneauPrincipal.add(panelCommandes, BorderLayout.SOUTH);

        setContentPane(panneauPrincipal);
    }

    private JPanel creerPanneauHaut() {
        JPanel panneauHaut = new JPanel(new BorderLayout());
        panneauHaut.setBackground(new Color(60, 63, 65));
        panneauHaut.setBorder(new EmptyBorder(10, 15, 10, 15));

        labelTitre = new JLabel("SAE - Robots mineurs");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 22));
        labelTitre.setForeground(Color.WHITE);

        labelTour = new JLabel("Tour : " + monde.getNumeroTour());
        labelTour.setFont(new Font("Arial", Font.BOLD, 18));
        labelTour.setForeground(Color.WHITE);

        panneauHaut.add(labelTitre, BorderLayout.WEST);
        panneauHaut.add(labelTour, BorderLayout.EAST);

        return panneauHaut;
    }

    public void rafraichir() {
        labelTour.setText("Tour : " + monde.getNumeroTour());
        panelGrille.rafraichir();
        panelInfos.rafraichir();
        repaint();
    }
}