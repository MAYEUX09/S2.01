package Jeu;

public class Main {
    public static void main(String[] args) {
        Monde m = new Monde();
        m.initialisation();

        while (true) {
            m.tour();
        }
    }
}