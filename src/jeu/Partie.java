package jeu;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @brief Classe gérant le déroulement de la partie.
 */
public class Partie {
    private Joueur[] tabJoueur = new Joueur[MAX_JOUEURS]; // Tableau contenant les deux joueurs qui s'affrontent.
    private static final int MAX_JOUEURS = 2; // Maximum de joueurs pouvant jouer.
    private int tour = 0; // Tour actuel (débute au tour 0).

    /**
     * @brief Initialise le tableau de joueurs.
     */
    public Partie(){
        for (int i = 0; i < MAX_JOUEURS; i++)
            tabJoueur[i] = new Joueur();
        //Start();
    }

    /**
     * @brief Traite la ligne entrée par le joueur dont c'est le tour. Exécute le coup si l'entrée est valide. Redemande une entrée sinon.
     * @param s [String] : Ligne à traiter entrée par le joueur.
     * @return [boolean] : Renvoie la validité de l'input.
     */
    public boolean traiterInput(String s){
        String[] tab;
        int count = 0;

        count = s.length() - s.replace("'", "").length();
        if (!s.equals("") && count<=1) {
            tab = decompose(s);
            if (checkFormatConditions(tab) && tab.length >= 2) {
                if (traiterInputBonFormat(tab))
                    return false;
                else
                    return true;
            }
            else
                return true;
        }
        else {
            return true;
        }
    }


    /**
     * @brief Renvoie la chaîne de caractères concernant les cartes posées et les cartes piochées.
     * @return [String] : Chaîne de caractères des cartes jouées et piochées.
     */
    public String StringDeCartesAddedEtRemoved(){
        return tabJoueur[(tour+1)%2].toStringNbCartesAddedAndRemoved();
    }

    /**
     * @brief Renvoie la chaîne de caractères de fin de partie, indiquant le gagnant de la partie.
     * @return [String] : Chaîne de caractères de fin de partie.
     */
    public String gagnantString(){
        StringBuilder s = new StringBuilder();
        s.append("partie finie, ");
        if ((tour+1)%2 == 0)
            s.append("NORD a gagné");
        else
            s.append("SUD a gagné");
        return s.toString();
    }

    /**
     * @brief Range les cartes jouées contenues dans le tableau tab dans les tableaux de cartes ascendantes (tabCarteAsc) et descendantes(tabCarteDesc).
     * @param tab [String[]] : Tableau des chaînes de caractères désignant chaque coup joué.
     * @param tabCarteAsc [ArrayList<Integer>] : Tableau des valeurs des cartes jouées sur la pile ascendante.
     * @param tabCarteDesc [ArrayList<Integer>] : Tableau des valeurs des cartes jouées sur la pile descendante.
     * @return [int] : Renvoie la valeur de l'index de la carte jouée sur les piles de l'adversaire dans le tableau tab si le joueur a joué sur son adversaire ou -1 sinon.
     */
    private int getCartes(String[] tab, ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc){
        int carteSurEnnemi = -1;
        for (int i = 0; i<tab.length; i++) {

            if (jouerSurAdversaire(tab[i])){
                //System.out.println("TEST ENNEMI 2");
                carteSurEnnemi = i;
                continue;
            }

            if (isMotAsc(tab[i])) {
                //System.out.println("TEST CONTINUE " + getCarteValue(tab[i]));
                tabCarteAsc.add(getCarteValue(tab[i]));
            }
            else {
                //System.out.println("TEST CONTINUE " + getCarteValue(tab[i]));
                tabCarteDesc.add(getCarteValue(tab[i]));
            }
        }
        return carteSurEnnemi;
    }


    /**
     * @brief Vérifie si les coups entrés par le joueur sont valides.
     * @param tab [String[]] : Tableau des chaînes de caractères désignant chaque coup joué.
     * @return [boolean] : La validité des coups du joueur.
     */
    private boolean traiterInputBonFormat(String[] tab) {
        ArrayList<Integer> tabCarteAsc = new ArrayList<>(), tabCarteDesc = new ArrayList<>();
        int carteSurEnnemi = getCartes(tab, tabCarteAsc, tabCarteDesc);
        boolean coupEnnemiAsc = false;

        if(carteSurEnnemi != -1){
            coupEnnemiAsc = isMotAsc(tab[carteSurEnnemi]);
            carteSurEnnemi = getCarteValue(tab[carteSurEnnemi]);
        }

        if (IsSaisieJouable(tabCarteAsc,tabCarteDesc, carteSurEnnemi, coupEnnemiAsc)){
            // ajouterCarte
            this.jouerLesCartes(carteSurEnnemi, coupEnnemiAsc, tabCarteAsc, tabCarteDesc);
            this.tour++;
            return true;
        }
        else
           //  System.out.println("Saisie non jouable");
            return false;
    }

    /**
     * @brief Joue les entrées des joueurs : Actualise les bases, retire les cartes jouées des mains et pioche les cartes à piocher.
     * @param carteSurEnnemi [int] : Valeur de la carte jouée sur l'adversaire.
     * @param coupEnnemiAsc [boolean] : Contient vrai si le coup sur adversaire est ascendant, faux sinon.
     * @param tabCarteAsc [ArrayList<Integer>] : Tableau contenant les valeurs des cartes jouées sur la pile ascendante.
     * @param tabCarteDesc [ArrayList<Integer>] : Tableau contenant les valeurs des cartes jouées sur la pile descendante.
     */
    private void jouerLesCartes(int carteSurEnnemi, boolean coupEnnemiAsc, ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc){
        if(carteSurEnnemi != -1){
            //System.out.println("TEST ENNEMI");
            tabJoueur[(this.tour + 1) % 2 ].ajouterCarteBase(carteSurEnnemi, coupEnnemiAsc);
        }
        if(!tabCarteAsc.isEmpty())
            tabJoueur[this.tour % 2].ajouterCarteBase(tabCarteAsc.get(tabCarteAsc.size() - 1), true);
        if(!tabCarteDesc.isEmpty())
            tabJoueur[this.tour % 2].ajouterCarteBase(tabCarteDesc.get(tabCarteDesc.size() - 1), false);

        tabJoueur[tour%2].removeCartesAndAddCartes(tabCarteDesc, tabCarteAsc, carteSurEnnemi);
    }

    /**
     * @brief Vérifie si la saisie entrée par le joueur est jouable. Vérifie si les valeurs des cartes jouées peuvent être posées sur leurs bases respectives.
     * @param tabCarteAsc [ArrayList<Integer>] : Tableau contenant les valeurs des cartes jouées sur la pile ascendante.
     * @param tabCarteDesc [ArrayList<Integer>] : Tableau contenant les valeurs des cartes jouées sur la pile descendante.
     * @param coupEnnemi [int] : Valeur de la carte jouée sur l'adversaire si le joueur a joué sur son adversaire, -1 sinon.
     * @param CoupEnnemiAsc [boolean] : Vrai si la carte jouée sur adversaire a été jouée sur la pile ascendante, faux sinon.
     * @return
     */
    private boolean IsSaisieJouable(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int coupEnnemi, boolean CoupEnnemiAsc){
        int adversaire = tour%2;

        if (!tabJoueur[adversaire].areCartesInMain(tabCarteAsc, tabCarteDesc, coupEnnemi)) {
            // System.out.println("ERREUR 5: Les cartes ne sont pas dans ta main");
            return false;
        }

        if (!triSaisie(tabCarteAsc, true) || !triSaisie(tabCarteDesc, false)){
            // System.out.println("ERREUR 1: Input non trié");
            return false;
        }

        if (coupEnnemi != -1){
            if (!tabJoueur[(this.tour + 1) % 2 ].isCartePosableEnnemi(coupEnnemi, CoupEnnemiAsc)){
                // System.out.println("ERREUR 2: Coup adversaire non posable");
                return false;
            }
        }

        if (tabCarteAsc.size() != 0){
           // System.out.println("ERREUR3");
            if (!tabJoueur[adversaire].isCartePosable(tabCarteAsc.get(0), true)){
                // System.out.println("ERREUR 3: Coup ascendant non posable");
                return false;
            }
        }
        if (tabCarteDesc.size() != 0){
           // System.out.println("ERREUR4");
            if (!tabJoueur[adversaire].isCartePosable(tabCarteDesc.get(0), false)){
                // System.out.println("ERREUR 4: Coup descendant non posable");
                return false;
            }
        }

        return true;
    }


    /**
     * @brief Vérifie si la saisie du joueur est triée. Carte ascendantes dans l'ordre croissant et les descendantes dans l'ordre décroissant.
     * @param tab [ArrayList<Integer>] : Valeurs des cartes à vérifier.
     * @param estAsc [boolean] : Vrai si il sagit de cartes jouées sur la pile ascendante, faux sinon.
     * @return [boolean] : Vrai si la saisie est triée, faux sinon.
     */
    private static boolean triSaisie(ArrayList<Integer> tab, boolean estAsc){
        boolean expr;
        if(tab.size()==1)
            return true;
        /*for(int num : tab)
            System.out.println("TAB = " + num + "\n");*/
        for (int i = 0; i<tab.size() - 1; i++){
            int j = i+1;
//                System.out.println("COMP : " + tab.get(i)+ " et " + tab.get(j) + " estAsc = " + estAsc);
            if(estAsc)
                expr = tab.get(j) > tab.get(i) || tab.get(j)+10 == tab.get(i);
            else
                expr = tab.get(i) > tab.get(j) || tab.get(j)-10 == tab.get(i);
            if (!expr)
                return false;
        }
        return true;
    }

    /**
     * @brief Récupère la valeur d'une carte contenue dans une chaîne de caractères.
     * @param mot [String] : Chaîne de caractères contenant la valeur d'une carte.
     * @return [int] : Valeur de la carte contenue dans la chaîne de caractères.
     */
    private static int getCarteValue(String mot) {
        int carte;
        if (Character.isDigit(mot.charAt(1)))
            carte = Integer.parseInt("" + mot.charAt(0) + mot.charAt(1));
        else
            carte = Integer.parseInt("" + mot.charAt(0));
        return carte;
    }

    /**
     * @brief Vérifie si le coup joué est ascendant ou non.
     * @param mot [String] : Chaîne de caractère contenant un coup joué.
     * @return [boolea] : Renvoie vrai si le coup est ascendant, faux sinon.
     */
    private static boolean isMotAsc(String mot){
        return mot.contains("^");
    }

    /**
     * @brief Vérifie si une suite de coup joués est conforme au format d'entrée attendu.
     * @param tab [String[]] : Tableau contenant les chaînes de caractères des coups joués.
     * @return [boolean] : Renvoie vrai si l'ensemble de coup est conforme au format, faux sinon.
     */
    private static boolean checkFormatConditions(String[] tab){
        for (String mot : tab) {
           // System.out.println("MOT :" + mot);
            if (!(mot.matches("[0-5]?[0-9][v^]'?")))
                return false;
        }
        return true;
    }

    /**
     * @brief Vérifie si le coup joué est joué sur l'adversaire.
     * @param mot [String] : Chaîne de caractères contenant le coup joué.
     * @return [boolean] : Renvoie vrai si le coup est joué sur l'adversaire, faux sinon.
     */
    private static boolean jouerSurAdversaire(String mot){
        return mot.contains("'");
    }

    /**
     * @brief Décompose une série de coup en un tableau de chaîne de caractères de chaque coup joué.
     * @param s [String] : Chaîne de caractères contenant la série de coups joués.
     * @return [String[]] : Tableau de chaîne de caractères contenant chaque coup de la série de coup joués.
     */
    private static String[] decompose(String s) {
        // une solution
        return s.split("\\s+");
    }

    /**
     * @brief Vérifie si la partie peut continuer ou non.
     * @return [boolean] : Renvoie true si la partie peut continuer, faux sinon.
     */
    public boolean continuer(){
        //System.out.println("PTITE STRING AVANT " + tour%2);
        return tabJoueur[tour%2].peutJouer(tabJoueur[(tour + 1)%2].getTopPileAsc(), tabJoueur[(tour + 1)%2].getTopPileDesc());
    }

    /**
     * @brief Créer la chaîne de caractères qui affiche les informations à afficher à chaque fin de tour.
     * @return [String] : Chaîne de carctères des informations à afficher chaque tour.
     */
    public String affiche(){
        StringBuilder s = new StringBuilder();
        s.append("NORD ");
        s.append(tabJoueur[0].toString());
        s.append("\n");
        s.append("SUD  ");
        s.append(tabJoueur[1].toString());
        s.append("\ncartes ");
        if(this.tour % 2 == 0){
            s.append("NORD ");
            s.append(tabJoueur[0].afficheMain());
        }
        else{
            s.append("SUD ");
            s.append(tabJoueur[1].afficheMain());
        }
        return s.toString();
    }
}
