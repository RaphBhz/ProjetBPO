package jeu;

import cartes.Base;
import cartes.Cartes;

import java.util.ArrayList;

public class Joueur {
    private static int compteurJoueurs = 0;
    private int id;
    private Base base = new Base();
    private Cartes cartes = new Cartes();

    public Joueur(int id){
        this.id = compteurJoueurs++;
    }

    public void ajouterCarteBase(int carte, boolean pileAsc){
        if (pileAsc)
            base.addCartePileAsc(carte);
        else
            base.addCartePileDesc(carte);
        return;
    }

    public boolean isCartePosable(int carte, boolean pileAsc){
        if (pileAsc)
            return carte > base.getTopPileAsc() || carte+10 == base.getTopPileAsc(); // Si la carte est sup au sommet de la carte de la pile asc ou si la carte est + petite de 10 que le sommet de la carte de la pile asc
        else
            return carte > base.getTopPileDesc() || carte-10 == base.getTopPileDesc();
    }

    public boolean peutJouer(){
        return !(this.cartes.piocheVide() && this.cartes.mainVide());
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(this.base.toString());
        s.append(" (m");
        s.append(this.cartes.nbCartesMain());
        s.append('p');
        s.append(this.cartes.nbCartes());
        s.append(")");
        return s.toString();
    }

    public int nbCartesMain(){
        return this.cartes.nbCartesMain();
    }

    public String afficheMain(){
        StringBuilder s = new StringBuilder();
        s.append("{ ");
        s.append(this.cartes.afficheMain());
        s.append("}");
        return s.toString();
    }

    public void piocher(int numCarte){
        this.cartes.piocher(numCarte);
        return;
    }
}
