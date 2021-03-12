package jeu;

import cartes.Base;
import cartes.Cartes;

import java.util.ArrayList;

public class Joueur {
    private int nbCartesRemovedFromHand = -1, nbCartesAddedToHand = -1;
    private Base base = new Base();
    private Cartes cartes = new Cartes();

    private final int[] mainImpossible  = {};

    /*
    public Joueur(int id){
        this.id = compteurJoueurs++;
    }
    */

    public void ajouterCarteBase(int carte, boolean pileAsc){
        if (pileAsc)
            base.addCartePileAsc(carte);
        else
            base.addCartePileDesc(carte);
    }

    public boolean isCartePosable(int carte, boolean pileAsc){
        if (pileAsc)
            return carte > base.getTopPileAsc() || carte+10 == base.getTopPileAsc(); // Si la carte est sup au sommet de la carte de la pile asc ou si la carte est + petite de 10 que le sommet de la carte de la pile asc
        else
            return carte < base.getTopPileDesc() || carte-10 == base.getTopPileDesc();
    }

    public boolean isCartePosableEnnemi(int carte, boolean pileAsc){
        if(pileAsc)
            return carte < this.base.getTopPileAsc();
        else
            return carte > this.base.getTopPileDesc();
    }

    public boolean peutJouer(int ascEnnemi, int descEnnemi){
        if((this.cartes.piocheVide() && this.cartes.nbCartesMain() == 0) || this.cartes.oneCarteInHandAndZeroInPioche()) {
            System.out.println("Erreur 9: Pas assez de cartes dans la main et dans la pioche");
            return false;
        }
        int cpt = 0;
        boolean coupEnnemi = false;
        for(int carte : this.cartes.getMain()){
            if(!coupEnnemi){
                if(carte > descEnnemi && carte < ascEnnemi){
                    coupEnnemi = true;
                    cpt++;
                    continue;
                }
            }
            if(carte < this.base.getTopPileDesc() || carte - 10 == this.base.getTopPileDesc() || carte > this.base.getTopPileAsc() || carte + 10 == this.base.getTopPileAsc())
                cpt++;
            if(cpt>=2){
                //System.out.println("PEUT JOUER");
                return true;
            }
        }
        //return !(this.cartes.piocheVide() && this.cartes.mainVide() == 0) && this.cartes.oneCarteInHandAndZeroInPioche();
        System.out.println("Erreur 10: Pas assez de cartes jouables. Nombre de cartes jouables : " + cpt);
        return false;
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

    public String toStringNbCartesAddedAndRemoved(){
        StringBuilder s = new StringBuilder();
        s.append(this.nbCartesRemovedFromHand).append(" cartes posées, ");
        s.append(this.nbCartesAddedToHand).append(" cartes piochées");
        return s.toString();
    }

    public void piocher(int numCarte){
        this.cartes.piocher(numCarte);
    }

    public boolean areCartesInMain(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int coupEnnemi){
        ArrayList<Integer> tabCarteTemp = new ArrayList<>(tabCarteAsc);
        tabCarteTemp.addAll(tabCarteDesc);
        if (coupEnnemi != -1)
            tabCarteTemp.add(coupEnnemi);
        for (Integer value : tabCarteTemp){
            if (!cartes.isCarteInMain(value))
                return false;
        }
        return true;

    }

    public int getTopPileAsc(){
        return this.base.getTopPileAsc();
    }

    public int getTopPileDesc(){
        return this.base.getTopPileDesc();
    }

    public void removeCartesAndAddCartes(ArrayList<Integer> tabCarteDesc, ArrayList<Integer> tabCarteAsc, int carteSurEnnemi) {

        this.nbCartesRemovedFromHand = cartes.removeCartes(tabCarteAsc, tabCarteDesc, carteSurEnnemi);
        this.nbCartesAddedToHand = cartes.addCartes(carteSurEnnemi);

    }
}
