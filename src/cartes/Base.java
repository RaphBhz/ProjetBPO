package cartes;

import java.util.Stack;

/**
 * @brief Informations concernant la base d'un joueur.
 */
public class Base {
    private int carteAscendante = 1; // Valeur présente sur le sommet de la pile ascendante du joueur.
    private int carteDescendante = 60; // Valeur présente sur le sommet de la pile descendante du joueur.

    /**
     * @brief Renvoie la carte au sommet de la pile ascendante.
     * @return [int] : Valeur de la carte au sommet de la pile ascendante.
     */
    public int getTopPileAsc(){
        return this.carteAscendante;
    }

    /**
     * @brief Renvoie la carte au sommet de la pile descendante.
     * @return [int] : Valeur de la carte au sommet de la pile descendante.
     */
    public int getTopPileDesc(){
        return this.carteDescendante;
    }

    /**
     * Remplace la carte au sommet de la pile descendante de la base du joueur.
     * @param carte [int] : Valeur de la carte à placer.
     */
    public void addCartePileDesc(int carte){this.carteDescendante = carte;}

    /**
     * Remplace la carte au sommet de la pile descendante de la base du joueur.
     * @param carte [int] : Valeur de la carte à placer.
     */
    public void addCartePileAsc(int carte){this.carteAscendante = carte;}

    /**
     * Renvoie la chaîne de caractères informant des cartes présentes sur les piles de la base d'un joueur.
     * @return [String] : Chaîne de caractères informant des cartes présentes sur les piles de la base d'un joueur.
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("^[");
        if(this.carteAscendante<10)
            s.append("0");
        s.append(this.carteAscendante);
        s.append("] v[");
        if(this.carteDescendante<10)
            s.append("0");
        s.append(this.carteDescendante);
        s.append("]");
        return s.toString();
    }
}
