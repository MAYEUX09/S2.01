package Jeu;

import affichage.Fenetrejeu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Monde m = new Monde();
        m.initialisation();

        Scanner scanner = new Scanner(System.in);


        System.out.println("Bienvenue dans la simulation minanière !");
        System.out.println("Choisissez votre mode d'affichage :");
        System.out.println("1 - Mode Console (Texte)");
        System.out.println("2 - Mode Graphique (Fenêtre 2D)");
        System.out.print("Votre choix (1 ou 2) : ");

        int choix = scanner.nextInt();

        if (choix == 1) {
            System.out.println("Lancement du mode console...");
            while (true) {
                m.tour();
            }
        } else if (choix == 2) {
            System.out.println("Lancement du mode graphique...");
            Fenetrejeu maFenetre = new Fenetrejeu(m);
            maFenetre.setVisible(true);
        } else {
            System.out.println("Choix invalide. Fin du programme.");
        }

        scanner.close();
    }
}