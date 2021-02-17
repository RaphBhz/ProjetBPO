package jeu;

public class Joueur {
    private String id;
    public cartes.Base base;
    public cartes.CartesMain main;
    public cartes.Pioche pioche;

    public Joueur(String id){
        this.id = id;
        this.base = new cartes.Base();
        this.main = new cartes.CartesMain();
        this.pioche = new cartes.Pioche();
    }


}
