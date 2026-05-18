package affichage;

import Jeu.*;
import affichage.fenetredejeu2;
import javax.swing.SwingUtilities;

public class Main2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Monde monde = new Monde();
            monde.initialisation();

            fenetredejeu2 fenetre = new fenetredejeu2(monde);
            fenetre.setVisible(true);
        });
    }
}
