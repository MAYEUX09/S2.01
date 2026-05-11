package Jeu;

public class Entrepot {

    private int id;
    private int capaciteActuel;
    private Secteur entrepot;
    private Minerais typeMinerais;

    public Entrepot(int id, int capaciteActuel, Secteur entrepot, Minerais typeMinerais) {
        this.id = id;
        this.capaciteActuel = capaciteActuel;
        this.entrepot = entrepot;
        this.typeMinerais = typeMinerais;
    }

    public void Stocker(int Quantite) {
        this.capaciteActuel += Quantite;
    }

}