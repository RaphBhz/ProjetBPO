package appli;

import jeu.Joueur;

public class Application {
    public static void main(String[] args) {
        Joueur NORD = new Joueur("NORD");
        Joueur SUD = new Joueur("SUD");

        boolean continuerJeu = true;

/*        while(continuerJeu){
            if(NORD.pioche.piocheVide() || SUD.pioche.piocheVide())
                continuerJeu = false;

        }

 */
        System.out.println(NORD.toString());
        System.out.println(SUD.toString());
        System.out.println("cartes " + NORD.id + ' ' + NORD.main.toString());

    }
}
