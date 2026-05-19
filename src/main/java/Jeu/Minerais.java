package Jeu;

public abstract class Minerais {

    private int nombreTotalMinerais;
    private String nomDuType;

    public Minerais(int quantiteInitiale, String nomDuType){
        this.nombreTotalMinerais = quantiteInitiale;
        this.nomDuType = nomDuType;
    }

    public int getNbMinerais(){
        return this.nombreTotalMinerais;
    }

    public String getType(){
        return this.nomDuType;
    }
}