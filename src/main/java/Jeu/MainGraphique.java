package Jeu;

import affichage.fenetredejeu2;
import javax.swing.SwingUtilities;

public class MainGraphique {
    public static void main(String[] args) {
        Monde m = new Monde();
        m.initialisation();

        System.out.println("Lancement du mode graphique...");
        SwingUtilities.invokeLater(() -> {
            fenetredejeu2 fenetre = new fenetredejeu2(m);
            fenetre.setVisible(true);
        });
    }
}