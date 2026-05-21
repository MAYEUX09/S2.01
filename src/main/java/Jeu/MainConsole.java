package Jeu;

public class MainConsole {
    public static void main(String[] args) {
        Monde m = new Monde();
        m.initialisation();

        System.out.println("Lancement du mode console...");
        while (true) {
            m.tour();
        }
    }
}