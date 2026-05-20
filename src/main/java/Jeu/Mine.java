package Jeu;

public class Mine {
    private int identifiant;
    private String typeDeMinerai; // "NI" ou "OR"
    private int capaciteMaximale;
    private int quantiteRestante;

    public Mine(int id, String type, int capMax, int capDepart) {
        this.identifiant = id;
        this.typeDeMinerai = type;
        this.capaciteMaximale = capMax;
        this.quantiteRestante = capDepart;
    }

    public int extraire(int quantiteSouhaitee) {
        int quantiteExtraiteReelle = Math.min(quantiteSouhaitee, this.quantiteRestante);
        this.quantiteRestante -= quantiteExtraiteReelle;
        return quantiteExtraiteReelle;
    }

    public int getId() {
        return identifiant;
    }

    public String getTypeMinerai() {
        return typeDeMinerai;
    }


    public int getCapaciteActuel() {
        return quantiteRestante;
    }

    public int getCapaciteMax() {
        return capaciteMaximale;
    }
}