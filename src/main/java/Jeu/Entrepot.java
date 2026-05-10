package Jeu;


public class Entrepot {


    private int id;
    private String minerais;
    private int ligne;
    private int colonne;
    private int stock;


    public Entrepot(int id, String minerais, int ligne, int colonne) {
        this.id = id;
        this.minerais = minerais;
        this.ligne = ligne;
        this.colonne = colonne;
        this.stock = 0;
    }


    public int getId(){ return id;}
    public String getMinerais(){ return minerais; }
    public int getLigne(){ return ligne; }
    public int getColonne(){ return colonne; }
    public int getStock(){ return stock; }

    // Déposer des minerais
    public void deposer(int quantite) {
        this.stock += quantite;
    }

    @Override
    public String toString() {
        return "E" + id + " " + ligne + " " + colonne +
                " " + minerais + " " + stock;
    }
}