package cartes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * @brief Classe contenant les informations sur les cartes dans la pioche et dans la main d'un joueur.
 */
public class Deck {
    private static final int MAX_PIOCHE = 58; // Maximum de cartes qui peuvent être présentes dans la pioche d'un joueur.
    private static final int MAX_MAIN = 6; // Maximum de cartes qui peuvent être présentes dans la main d'un joueur.
    private Stack<Integer> cartesPioche = new Stack<Integer>(); // Cartes de la pioche d'un joueur.
    private int[] cartesEnMain =  new int[MAX_MAIN]; // Cartes de la main d'un joueur.

    /**
     * @brief Constructeur des cartes de la pioche et de la main d'un joueur.
     */
    public Deck(){
        addAllCartesToPioche();
        Collections.shuffle(cartesPioche);
        pickCartesDepart();
    }

    /**
     * @brief Pioche les cartes de départ pour la main d'un joueur.
     */
    private void pickCartesDepart(){
        for (int i = 0; i< MAX_MAIN; i++){
            cartesEnMain[i]=cartesPioche.pop();
        }
        Arrays.sort(cartesEnMain);
    }

    /**
     * @brief Ajout de toutes les cartes jouables dans la pioche.
     */
    private void addAllCartesToPioche(){
        for(int i = 0; i < MAX_PIOCHE; i++){
            cartesPioche.push(i+2);

        }
    }

    /**
     * @brief Vérifie la présence d'une valeur de carte dans la main d'un joueur.
     * @param carte [int] : Valeur de la carte à vérifier.
     * @return [boolean] : Renvoie vrai si la carte est présente, faux sinon.
     */
    public boolean isCarteInMain(int carte){
        for (int value : cartesEnMain){
            if (value == carte)
                return true;
        }
        return false;
    }

    /**
     * @brief Enlève les cartes qui viennent d'être jouées de la main d'un joueur.
     * @param tabCarteAsc [ArrayList<Integer>] : Cartes qui ont été jouées sur la pile ascendante.
     * @param tabCarteDesc [ArrayList<Integer>] : Cartes qui ont été jouées sur la pile descendante.
     * @param carteSurEnnemi [int] : Valeur de la carte qui a été jouée sur la base adverse, -1 si cela n'a pas été fait.
     * @return [int] : Nombre de cartes enlevées.
     */
    public int removeCartes(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int carteSurEnnemi){

        int cpt = 0;
        ArrayList<Integer> tabCarteTemp = new ArrayList<>(tabCarteAsc);
        tabCarteTemp.addAll(tabCarteDesc);
        if (carteSurEnnemi != -1)
            tabCarteTemp.add(carteSurEnnemi);

        for (int i = 0; i<MAX_MAIN; i++){

            for (Integer integer : tabCarteTemp)
                if (integer == cartesEnMain[i]) {
                    cartesEnMain[i] = -1;
                    cpt++;
                    break;
                }

        }
        return cpt;
    }

    /**
     * @brief Pioche des cartes et les ajoute dans la main d'un joueur.
     * @param carteSurEnnemi [int] : Valeur de la carte qui a été jouée sur la base adverse, -1 si cela n'a pas été fait.
     * @return [int] : Nombre de cartes piochées.
     */
    public int addCartes(int carteSurEnnemi){
        int nbCartesAdded = 0;
        for (int i = 0; i<MAX_MAIN; i++){
            if (cartesPioche.size() == 0)
                break;

            if (cartesEnMain[i] == -1){
                cartesEnMain[i] = cartesPioche.pop();
                nbCartesAdded+=1;
            }
            if (carteSurEnnemi == -1 && nbCartesAdded == 2)
                break;
        }
        Arrays.sort(cartesEnMain);
        return nbCartesAdded;
    }

    /**
     * @brief Vérifie si la pioche d'un joueur est vide.
     * @return [boolean] : Vrai si la pioche est vide, faux sinon.
     */
    public boolean piocheVide(){
        return this.cartesPioche.empty();
    }

    /**
     * @brief Vérifie si un joueur n'a plus qu'une carte.
     * @return [boolean] : Vrai si le joueur n'a plus qu'une carte, faux sinon.
     */
    public boolean oneCarteInHandAndZeroInPioche(){
        return cartesPioche.size() == 0 && nbCartesMain() <2;
    }

    /**
     * @brief Renvoie le nombre de cartes dans la pioche d'un joueur.
     * @return [int] : Nombre de cartes dans la pioche du joueur.
     */
    public int nbCartes(){
        return cartesPioche.size();
    }

    /**
     * @brief Renvoie le nombre de cartes présentes dans la main d'un joueur.
     * @return [int] : Nombre de cartes présentes dans la main du joueur.
     */
    public int nbCartesMain(){
        int c = 0;
        for(int numCarte : this.cartesEnMain){
            if(numCarte != -1)
                c++;
        }
        return c;
    }

    /**
     * @brief Renvoie une chaîne de caractères qui contient les cartes présentes dans la main d'un joueur.
     * @return [String] : Chaîne de caractères qui contient les cartes présentes dans la main d'un joueur.
     */
    public String afficheMain(){
        StringBuilder s = new StringBuilder();
        //Arrays.sort(cartesEnMain);
        for(int carte : cartesEnMain){
            if(carte != -1){
                if(carte<10)
                    s.append("0");
                s.append(carte).append(" ");
            }
        }
        return s.toString();
    }

    /**
     * @brief Clone la main d'un joueur.
     * @return [int[]] : Clone des cartes de la main d'un joueur.
     */
    public int[] getMain(){
        return cartesEnMain.clone();
    }
}
