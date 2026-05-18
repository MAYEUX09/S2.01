package Jeu;

public class Entrepot {
    private int identifiant;
    private String typeDeMineraiAccepte; // "NI" ou "OR"
    private int quantiteStockeeActuelle;

    public Entrepot(int id, String type, int ligne, int colonne) {
        this.identifiant = id;
        this.typeDeMineraiAccepte = type;
        this.quantiteStockeeActuelle = 0;
    }

    public void stocker(int quantiteAAjouter) {
        this.quantiteStockeeActuelle += quantiteAAjouter;
    }


    public int getId() {
        return identifiant;
    }

    public String getTypeMinerai() {
        return typeDeMineraiAccepte;
    }

    public int getStockActuel() {
        return quantiteStockeeActuelle;
    }
}