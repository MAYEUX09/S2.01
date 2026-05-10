package Jeu;

public class Entrepot {

    // Attributs
    private int id;
    private String minerais;
    private int capacite;
    private int stock;

    // Constructeur
    public Entrepot(int id, String minerais, int capacite) {
        this.id = id;
        this.minerais = minerais;
        this.capacite = capacite;
        this.stock = 0;
    }

    public int getId() {
        return id;
    }

    public String getMinerais() {
        return minerais;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getStock() {
        return stock;
    }

    public void ajouterStock(int quantite) {
        this.stock += quantite;

        // Si le stock dépasse la capacité maximale, on le ramène à la capacité
        if (this.stock > this.capacite) {
            this.stock = this.capacite;
        }
    }

    // Affichage
    @Override
    public String toString() {
        return "Entrepot[id=" + id + ", type=" + minerais +
                ", stock=" + stock + "/" + capacite + "]";
    }
}