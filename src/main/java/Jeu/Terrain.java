package Jeu;
/**
 * Représente un terrain du monde.
 * Un terrain est un secteur accessible par les robots
 * s'il n'est pas occupé ou bloqué par une autre contrainte.
 */

public class Terrain {

    private int id;

    /**
     * Constructeur du terrain.
     * id identifiant du terrain
     */
    public Terrain(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Terrain " + id;
    }
}

