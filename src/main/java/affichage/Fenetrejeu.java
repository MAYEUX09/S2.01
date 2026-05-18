package affichage;

import Jeu.*;
import javax.swing.*;
import java.awt.*;

public class Fenetrejeu extends JFrame {
    private Monde monMonteurdeJeu;


    public Fenetrejeu (Monde monde){
        this.monMonteurdeJeu = monde;
        this.setTitle("Simulation de voilà");
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panneauPricipal = new JPanel();
        panneauPricipal.setLayout(new BoxLayout(panneauPricipal,BoxLayout.Y_AXIS));
        JLabel texte = new JLabel("Bienvenue dans le jeu");
        panneauPricipal.add(texte);
        JPanel panneauGrille = new JPanel();
        panneauGrille.setLayout(new GridLayout(10, 10));
        for (int ligne = 0; ligne < 10; ligne++)
        {
            for (int colonne = 0; colonne < 10; colonne++)
            {
                JButton boutonCase = new JButton();
                panneauGrille.add(boutonCase);
                Secteur secteuractuel = monMonteurdeJeu.getGrille()[ligne][colonne];
                if (secteuractuel.getEau() != null){
                    boutonCase.setBackground(Color.BLUE);
                }
                else {
                    boutonCase.setBackground(Color.WHITE);
                }
                panneauGrille.add(boutonCase);
            }
        }
        panneauPricipal.add(panneauGrille);
        this.setContentPane(panneauPricipal);
        }
}
