package Jeu;

public class Mine {

    private int id;
    private Minerais minerais;
    private int capacite;
    private int capaciteActuel;
    private Secteur mine;

    public Mine(int id, Minerais minerais, int capacite, int capaciteActuel, Secteur mine) {
        this.id = id;
        this.minerais = minerais;
        this.capacite = capacite;
        this.capaciteActuel = capaciteActuel;
        this.mine = mine;
    }

    public int extraire(int Quantité) {
        int nbrMineraisExtrait;
        nbrMineraisExtrait = 0;
        if (capaciteActuel >= Quantité) {
            capaciteActuel -= Quantité;
            nbrMineraisExtrait += Quantité;
        } else {
            nbrMineraisExtrait = capaciteActuel;
            capaciteActuel = 0;
        }
        return nbrMineraisExtrait;
    }

    public boolean estvide() {
        if (capaciteActuel == 0) {
            return true;
        } else {
            return false;
        }
    }
}