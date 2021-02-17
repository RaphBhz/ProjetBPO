package appli;

import jeu.Joueur;

public class Application {
    public static void main(String[] args) {
        Joueur NORD = new Joueur("NORD");
        Joueur SUD = new Joueur("SUD");

        boolean continuerJeu = true;

        while(continuerJeu){
            if(NORD.pioche.piocheVide() || SUD.pioche.piocheVide())
                continuerJeu = false;
        }
    }
}
