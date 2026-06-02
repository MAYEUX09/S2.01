package Jeu;

public class Terrain {

    private int identifiantUnique;

    public Terrain(int id) {
        this.identifiantUnique = id;
    }
    public int getId() {
        return this.identifiantUnique;
    }
}