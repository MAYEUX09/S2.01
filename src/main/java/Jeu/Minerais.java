package Jeu;

import java.util.*;

public abstract class Minerais {

    private int nbMinerais;
    private String type;


    public Minerais(int nbr, String nom){
    this.nbMinerais = nbr;
    this.type = nom;
    }

    public int getNbMineraise(){
        return this.nbMinerais;
    }

    public String getType(){
        return this.type;
    }
}