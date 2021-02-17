package jeu;

import cartes.Main;

public class Joueur {
    public String id;
    public cartes.Base base;
    public Main main;
    public cartes.Pioche pioche;

    public Joueur(String id){
        this.id = id;
        this.base = new cartes.Base();
        this.main = new cartes.Main();
        this.pioche = new cartes.Pioche();
        this.main.initialiser(this.pioche);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(this.id);
        s.append(this.base.toString());
        s.append(" (m");
        s.append(this.main.nbCartes());
        s.append('p');
        s.append(this.pioche.nbCartes());
        s.append(")");
        return s.toString();
    }
}
