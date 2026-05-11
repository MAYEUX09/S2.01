package Jeu;

public abstract class Minerais {

    private int nombreTotalMinerais;
    private String nomDuType;

    public Minerais(int quantiteInitiale, String nomLeType){
        this.nombreTotalMinerais = quantiteInitiale;
        this.nomDuType = nomLeType;
    }

    public int getNbMineraise(){
        return this.nombreTotalMinerais;
    }

    public String getType(){
        return this.nomDuType;
    }
}