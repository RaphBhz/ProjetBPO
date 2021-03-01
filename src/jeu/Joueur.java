package jeu;

import cartes.Base;
import cartes.Pioche;

public class Joueur {
    private static int compteurJoueurs = 0;
    private int id;
    private Base base = new Base();
    private Pioche pioche = new Pioche();

    public Joueur(int id){
        this.id = compteurJoueurs++;
    }

    public boolean ajouterCarte(int carte, boolean pileAsc){
        if (isCartePosable(carte, pileAsc)) {
            if (pileAsc)
                base.addCartePileAsc(carte);
            else
                base.addCartePileDesc(carte);
            return true;
        }
        return false;

    }

    public boolean isCartePosable(int carte, boolean pileAsc){
        if (pileAsc)
            return carte > base.getTopPileAsc() || carte+10 == base.getTopPileAsc(); // Si la carte est sup au sommet de la carte de la pile asc ou si la carte est + petite de 10 que le sommet de la carte de la pile asc
        else
            return carte > base.getTopPileDesc() || carte-10 == base.getTopPileDesc();
    }

    public boolean peutJouer(){
        return this.pioche.piocheVide() && this.pioche.mainVide();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(this.id);
        s.append(this.base.toString());
        s.append(" (m");
        s.append(this.pioche.nbCartes());
        s.append('p');
        s.append(this.pioche.nbCartes());
        s.append(")");
        return s.toString();
    }
}
