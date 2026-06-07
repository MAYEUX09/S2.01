package affichage;

import Jeu.*;
import Jeu.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PanelCommandes extends JPanel {

    private Monde monde;
    private fenetredejeu2 fenetre;
    private JComboBox<Robot> choixRobot;

    public PanelCommandes(Monde monde, fenetredejeu2 fenetre) {
        this.monde = monde;
        this.fenetre = fenetre;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Liste déroulante des robots disponibles
        choixRobot = new JComboBox<>();
        for (Robot robot : monde.getLesRobots()) {
            choixRobot.addItem(robot);
        }

        // Boutons d'action
        JButton boutonNord = new JButton("Nord");
        JButton boutonSud = new JButton("Sud");
        JButton boutonEst = new JButton("Est");
        JButton boutonOuest = new JButton("Ouest");
        JButton boutonRecolter = new JButton("Récolter");
        JButton boutonDeposer = new JButton("Déposer");
        JButton boutonAuto = new JButton("Auto-pilot (Dijkstra)");  // ← NOUVEAU BOUTON
        JButton boutonTour = new JButton("Tour suivant");

        add(new JLabel("Robot :"));
        add(choixRobot);
        add(boutonNord);
        add(boutonSud);
        add(boutonEst);
        add(boutonOuest);
        add(boutonRecolter);
        add(boutonDeposer);
        add(boutonAuto);
        add(boutonTour);

        // Association des actions aux boutons
        boutonNord.addActionListener(e -> deplacerRobot("nord"));
        boutonSud.addActionListener(e -> deplacerRobot("sud"));
        boutonEst.addActionListener(e -> deplacerRobot("est"));
        boutonOuest.addActionListener(e -> deplacerRobot("ouest"));
        boutonRecolter.addActionListener(e -> recolterRobot());
        boutonDeposer.addActionListener(e -> deposerRobot());
        boutonAuto.addActionListener(e -> autoPilotRobot());
        boutonTour.addActionListener(e -> passerTour());
    }

    private Robot robotSelectionne() {
        return (Robot) choixRobot.getSelectedItem();
    }

    private void deplacerRobot(String direction) {
        Robot robot = robotSelectionne();
        if (robot == null) return;

        int ancienneLigne = robot.getLigne();
        int ancienneColonne = robot.getColonne();
        int nouvelleLigne = ancienneLigne;
        int nouvelleColonne = ancienneColonne;

        if (robot.getCredit() == 1){
            if (direction.equals("nord")) nouvelleLigne--;
            if (direction.equals("sud")) nouvelleLigne++;
            if (direction.equals("est")) nouvelleColonne++;
            if (direction.equals("ouest")) nouvelleColonne--;
        }
        else{
            JOptionPane.showMessageDialog(this, "Tu as voulu tricher ? c'est mal ! vous avez déjà jouer");
        }

        boolean estDansLimites =
                nouvelleLigne >= 0 && nouvelleLigne < 10 &&
                        nouvelleColonne >= 0 && nouvelleColonne < 10;

        if (estDansLimites
                && monde.getGrille()[nouvelleLigne][nouvelleColonne].getEau() == null
                && monde.getGrille()[nouvelleLigne][nouvelleColonne].getRobot() == null) {

            monde.getGrille()[ancienneLigne][ancienneColonne].setRobot(null);
            robot.avancer(direction);
            monde.getGrille()[nouvelleLigne][nouvelleColonne].setRobot(robot);
            fenetre.rafraichir();
        } else {
            JOptionPane.showMessageDialog(this, "Mouvement impossible !");
        }
    }

    private void recolterRobot() {
        Robot robot = robotSelectionne();
        if (robot == null) return;

        Mine mine = monde.getGrille()[robot.getLigne()][robot.getColonne()].getMine();
        int quantite = robot.recolter(mine);

        if (quantite > 0 && robot.getCredit() == 1){
            JOptionPane.showMessageDialog(this, "Récolte de " + quantite + " minerai(x).");
        } else {
            JOptionPane.showMessageDialog(this, "Récolte impossible !");
        }

        fenetre.rafraichir();
    }


    private void deposerRobot() {
        Robot robot = robotSelectionne();
        if (robot == null) return;

        Entrepot entrepot = monde.getGrille()[robot.getLigne()][robot.getColonne()].getEntrepot();
        int quantite = robot.deposer(entrepot);

        if (quantite > 0 && robot.getCredit()==1) {
            JOptionPane.showMessageDialog(this, "Dépôt de " + quantite + " minerai(x).");
        } else {
            JOptionPane.showMessageDialog(this, "Dépôt impossible !");
        }

        fenetre.rafraichir();
    }

    // NOUVELLE MÉTHODE : Auto-pilot avec Dijkstra
    private void autoPilotRobot() {
        Robot robot = robotSelectionne();
        if (robot == null) return;

        if (robot.getCredit() == 0) {
            JOptionPane.showMessageDialog(this, "Ce robot a déjà joué ce tour !");
            return;
        }

        Secteur[][] grille = monde.getGrille();
        int targetLigne = -1, targetColonne = -1;
        String mode = robot.modeDuRobot();
        String specialisation = robot.getType();  // "NI" ou "OR"

        if (mode.equals("Entrepot")) {
            // Chercher l'entrepôt du bon type
            for (int l = 0; l < 10; l++) {
                for (int c = 0; c < 10; c++) {
                    Entrepot e = grille[l][c].getEntrepot();
                    if (e != null && e.getTypeMinerai().equals(specialisation)) {
                        targetLigne = l;
                        targetColonne = c;
                        break;
                    }
                }
                if (targetLigne != -1) break;
            }
        } else if (mode.equals("Mine")) {
            // Chercher la mine du bon type avec minerai disponible
            for (int l = 0; l < 10; l++) {
                for (int c = 0; c < 10; c++) {
                    Mine m = grille[l][c].getMine();
                    if (m != null && m.getTypeMinerai().equals(specialisation) && m.getCapaciteActuel() > 0) {
                        targetLigne = l;
                        targetColonne = c;
                        break;
                    }
                }
                if (targetLigne != -1) break;
            }
        }

        // Si on est déjà sur la cible
        Secteur sec = grille[robot.getLigne()][robot.getColonne()];
        if (mode.equals("Entrepot") && sec.getEntrepot() != null
                && sec.getEntrepot().getTypeMinerai().equals(specialisation)) {
            deposerRobot();  // Déposer directement
            return;
        } else if (mode.equals("Mine") && sec.getMine() != null
                && sec.getMine().getTypeMinerai().equals(specialisation)
                && sec.getMine().getCapaciteActuel() > 0) {
            recolterRobot();  // Récolter directement
            return;
        }

        if (targetLigne == -1) {
            JOptionPane.showMessageDialog(this, "Aucune cible valide trouvée !");
            return;
        }

        // Appel de executerDijkstra() pour obtenir la direction
        String direction = robot.executerDijkstra(grille, targetLigne, targetColonne);

        if (direction == null) {
            JOptionPane.showMessageDialog(this, "Pas de mouvement nécessaire ou chemin impossible !");
            return;
        }

        System.out.println("-> Le robot commence son trajet vers le " + direction);  // Comme dans Monde.tour()

        // Déplacer le robot d'un seul pas dans cette direction
        deplacerRobot(direction);
    }

    private void passerTour() {
        for (Robot robot : monde.getLesRobots()) {
            if (robot.getCredit() > 0) {
                JOptionPane.showMessageDialog(this, "Impossible tout les robots n'ont pas jouer");
                return;
            }
        }

        monde.incrementerTour();
        for (Robot robot : monde.getLesRobots()) {
            robot.resetcredit();
        }

        fenetre.rafraichir();
    }
}