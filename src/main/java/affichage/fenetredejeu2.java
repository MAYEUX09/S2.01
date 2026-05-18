package affichage;

import Jeu.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Fenêtre principale du jeu en mode graphique.
 * Elle contient :
 * - un bandeau supérieur avec le titre et le numéro du tour,
 * - la grille du monde,
 * - le panneau d'informations,
 * - le panneau de commandes.
 */
public class fenetredejeu2 extends JFrame {

    private Monde monde;
    private PanelGrille panelGrille;
    private PanelInfos panelInfos;
    private PanelCommandes panelCommandes;
    private JLabel labelTitre;
    private JLabel labelTour;

    /**
     * Constructeur de la fenêtre graphique.
     * @param monde instance du monde à afficher et à manipuler
     */
    public fenetredejeu2(Monde monde) {
        this.monde = monde;

        setTitle("Simulation robots mineurs");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal de la fenêtre
        JPanel panneauPrincipal = new JPanel(new BorderLayout(10, 10));
        panneauPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panneauPrincipal.setBackground(new Color(230, 230, 230));

        // Bandeau du haut
        JPanel panneauHaut = creerPanneauHaut();

        // Composants de la fenêtre
        panelGrille = new PanelGrille(monde);
        panelInfos = new PanelInfos(monde);
        panelCommandes = new PanelCommandes(monde, this);

        // Conteneur central pour mettre la grille et les infos côte à côte
        JPanel conteneurCentre = new JPanel(new BorderLayout(10, 10));
        conteneurCentre.setOpaque(false);
        conteneurCentre.add(panelGrille, BorderLayout.CENTER);
        conteneurCentre.add(panelInfos, BorderLayout.EAST);

        panneauPrincipal.add(panneauHaut, BorderLayout.NORTH);
        panneauPrincipal.add(conteneurCentre, BorderLayout.CENTER);
        panneauPrincipal.add(panelCommandes, BorderLayout.SOUTH);

        setContentPane(panneauPrincipal);
    }

    /**
     * Crée le panneau supérieur contenant le titre et le tour actuel.
     * @return le panneau du haut
     */
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

    /**
     * Rafraîchit l'affichage de la fenêtre après une action.
     */
    public void rafraichir() {
        labelTour.setText("Tour : " + monde.getNumeroTour());
        panelGrille.rafraichir();
        panelInfos.rafraichir();
        repaint();
    }
}