package jeu;

import cartes.Base;
import cartes.Deck;

import java.util.ArrayList;

/**
 * @brief Contient toutes les informations relatives aux joueurs : cartes en main, cartes sur les bases
 */
public class Joueur {
    private int nbCartesRemovedFromHand = -1, nbCartesAddedToHand = -1; // Nombre de cartes jouées et piochées au cours du tour courant du joueur.
    private Base base = new Base(); // Cartes des bases d'un joueur.
    private Deck deck = new Deck(); // Pack de cartes d'un joueur : main et pioche.

    /**
     * @brief Ajoute des cartes sur une des bases.
     * @param carte [int] : Valeur de la carte à poser.
     * @param pileAsc [boolean] : Vrai si la carte est à poser sur la pile ascendante, faux sinon.
     */
    public void ajouterCarteBase(int carte, boolean pileAsc){
        if (pileAsc)
            base.addCartePileAsc(carte);
        else
            base.addCartePileDesc(carte);
    }

    /**
     * @brief Vérifie si la carte à poser est posable sur la base sur laquelle elle doit être posée.
     * @param carte [int] : Valeur de la carte à poser.
     * @param pileAsc [boolean] : Vrai si la carte est jouée sur une pile ascendante, faux sinon.
     * @return [boolean] : Vrai si la carte est posable, faux sinon.
     */
    public boolean isCartePosable(int carte, boolean pileAsc){
        if (pileAsc)
            return carte > base.getTopPileAsc() || carte+10 == base.getTopPileAsc(); // Si la carte est sup au sommet de la carte de la pile asc ou si la carte est + petite de 10 que le sommet de la carte de la pile asc
        else
            return carte < base.getTopPileDesc() || carte-10 == base.getTopPileDesc();
    }

    /**
     * @brief Vérifie si la carte à poser est posable sur la base de l'adversaire sur laquelle elle doit être posée.
     * @param carte [int] : Valeur de la carte à poser.
     * @param pileAsc [boolean] : Vrai si la carte est jouée sur une pile ascendante, faux sinon.
     * @return [boolean] : Vrai si la carte est posable, faux sinon.
     */
    public boolean isCartePosableEnnemi(int carte, boolean pileAsc){
        if(pileAsc)
            return carte < this.base.getTopPileAsc();
        else
            return carte > this.base.getTopPileDesc();
    }

    /**
     * @brief Vérifie si un joueur peut jouer selon les cartes qu'il a en main et les cartes présentes sur les bases.
     * @param ascEnnemi [int] : Valeur de la carte sur le dessus de la pile ascendante ennemie.
     * @param descEnnemi [int] : Valeur de la carte sur le dessus de la pile descendante ennemie.
     * @return [boolean] : Renvoie vrai si le joueur peut jouer, faux sinon.
     */
    public boolean peutJouer(int ascEnnemi, int descEnnemi){
        if((this.deck.piocheVide() && this.deck.nbCartesMain() == 0) || this.deck.oneCarteInHandAndZeroInPioche()) {
            System.out.println("Erreur 9: Pas assez de cartes dans la main et dans la pioche");
            return false;
        }
        int cpt = 0;
        ArrayList<Integer> coupNonJouable = new ArrayList<>();
        for(int carte : this.deck.getMain()){

            if(carte < this.base.getTopPileDesc() || carte - 10 == this.base.getTopPileDesc() || carte > this.base.getTopPileAsc() || carte + 10 == this.base.getTopPileAsc())
                cpt++;
            else
                coupNonJouable.add(carte);
        }

        if(cpt>=2)
            return true;

        cpt+=checkCartesNonJouables(coupNonJouable, ascEnnemi, descEnnemi);

        if(cpt>=2) {
            //System.out.println("PEUT JOUER");
            return true;
        }
        else {
            System.out.println("Erreur 10: Pas assez de cartes jouables. Nombre de cartes jouables : " + cpt);
            return false;
        }
    }

    private int checkCartesNonJouables(ArrayList<Integer> coupNonJouable, int ascEnnemi, int descEnnemi){
        for(int carte : coupNonJouable){
            if(carte > descEnnemi || carte < ascEnnemi){
                return 1;
            }
        }
        return 0;
    }

    @Override
    /**
     * @brief Renvoie une chaîne de caractères contenant les informations d'un joueur.
     * @return [String] : Informations concernant le joueur dans une chaîne de caractères.
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(this.base.toString());
        s.append(" (m");
        s.append(this.deck.nbCartesMain());
        s.append('p');
        s.append(this.deck.nbCartes());
        s.append(")");
        return s.toString();
    }

    /**
     * @brief Renvoie une chaîne de caractères avec les cartes de la main d'un joueur.
     * @return [String] : Chaîne de caractères contenant les valeurs des cartes en main d'un joueur.
     */
    public String afficheMain(){
        StringBuilder s = new StringBuilder();
        s.append("{ ");
        s.append(this.deck.afficheMain());
        s.append("}");
        return s.toString();
    }

    /**
     * @brief Compte le nombre de cartes jouées et le nombre de cartes piochées au cours d'un tour.
     * @return [String] : Chaîne de caractères contenant le nombre de cartes posées et piochées.
     */
    public String toStringNbCartesAddedAndRemoved(){
        StringBuilder s = new StringBuilder();
        s.append(this.nbCartesRemovedFromHand).append(" cartes posées, ");
        s.append(this.nbCartesAddedToHand).append(" cartes piochées");
        return s.toString();
    }

    /**
     * @brief Vérifie si les cartes entreés en paramètre sont présentes dans la main de celui qui les a jouées.
     * @param tabCarteAsc [ArrayList<Integer>] : Cartes à jouer sur la pile ascendante.
     * @param tabCarteDesc [ArrayList<Integer>] : Cartes à jouer sur la pile descendante.
     * @param coupEnnemi [int] : Carte à jouer sur une bases adverses.
     * @return
     */
    public boolean areCartesInMain(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int coupEnnemi){
        ArrayList<Integer> tabCarteTemp = new ArrayList<>(tabCarteAsc);
        tabCarteTemp.addAll(tabCarteDesc);
        if (coupEnnemi != -1)
            tabCarteTemp.add(coupEnnemi);
        for (Integer value : tabCarteTemp){
            if (!deck.isCarteInMain(value))
                return false;
        }
        return true;

    }

    /**
     * @brief Renvoie la carte de la pile ascendante de la base d'un joueur.
     * @return [int] : Valeur de la carte de la pile ascendante du joueur.
     */
    public int getTopPileAsc(){
        return this.base.getTopPileAsc();
    }

    /**
     * @brief Renvoie la carte de la pile descendante de la base d'un joueur.
     * @return [int] : Valeur de la carte de la pile descendante du joueur.
     */
    public int getTopPileDesc(){
        return this.base.getTopPileDesc();
    }

    /**
     * @brief Enlève les cartes jouées de la main d'un joueur et ajoute les cartes piochées.
     * @param tabCarteDesc [ArrayList<Integer>] : Cartes jouées sur la pile ascendante.
     * @param tabCarteAsc [ArrayList<Integer>] : Cartes jouées sur la pile descendante.
     * @param carteSurEnnemi [int] : Carte jouée sur une des bases ennemie.
     */
    public void removeCartesAndAddCartes(ArrayList<Integer> tabCarteDesc, ArrayList<Integer> tabCarteAsc, int carteSurEnnemi) {

        this.nbCartesRemovedFromHand = deck.removeCartes(tabCarteAsc, tabCarteDesc, carteSurEnnemi);
        this.nbCartesAddedToHand = deck.addCartes(carteSurEnnemi);

    }
}
