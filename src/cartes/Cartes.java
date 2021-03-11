package cartes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Cartes {
    private static final int MAX_PIOCHE = 58;
    private Stack<Integer> cartesPioche = new Stack<Integer>();
    private static final int MAX_MAIN = 6;
    private int[] cartesEnMain =  new int[MAX_MAIN];

    public Cartes(){
        addAllCartesToPioche();
        Collections.shuffle(cartesPioche);
        pickCartesDepart();
    }

    private void pickCartesDepart(){
        for (int i = 0; i< MAX_MAIN; i++){
            cartesEnMain[i]=cartesPioche.pop();
        }
    }

    private void addAllCartesToPioche(){
        for(int i = 0; i < MAX_PIOCHE; i++){
            cartesPioche.push(i+2);

        }
    }

    public int mainVide(){
        int cpt = 0;
        for(int i = 0; i < MAX_MAIN; i++){
            if(cartesEnMain[i] != -1)
                cpt++;
        }
        return cpt;
    }

    public int pickCarte(){
        return cartesPioche.pop();
    }

    public void piocher(int numCarte){
        if(this.cartesEnMain.length == 6)
            return;
        this.cartesEnMain[Arrays.asList(cartesEnMain).indexOf(numCarte)] = this.pickCarte();
    }

    public boolean isCarteInMain(int carte){
        for (int value : cartesEnMain){
            if (value == carte)
                return true;
        }
        return false;
    }

    public void removeCartes(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int carteSurEnnemi){

        tabCarteAsc.addAll(tabCarteDesc);
        if (carteSurEnnemi != -1)
            tabCarteAsc.add(carteSurEnnemi);

        for (int i = 0; i<MAX_MAIN; i++){

            for (Integer integer : tabCarteAsc)
                if (integer == cartesEnMain[i]) {
                    cartesEnMain[i] = -1;
                    break;
                }

        }
    }

    public void addCartes(int carteSurEnnemi){
        int nbCartesAdded = 0;
        for (int i = 0; i<MAX_MAIN; i++){
            if (cartesPioche.size() == 0)
                break;

            if (cartesEnMain[i] == -1){
                cartesEnMain[i] = pickCarte();
                nbCartesAdded+=1;
            }
            if (carteSurEnnemi == -1 && nbCartesAdded == 2)
                break;

        }
    }

    public boolean piocheVide(){
        return this.cartesPioche.empty();
    }

    public boolean oneCarteInHandAndZeroInPioche(){
        return cartesPioche.size() == 0 && mainVide() <2;
    }

    public int nbCartes(){
        return cartesPioche.size();
    }

    public int nbCartesMain(){
        int c = 0;
        for(int numCarte : this.cartesEnMain){
            if(numCarte != -1)
                c++;
        }
        return c;
    }

    public String afficheMain(){
        StringBuilder s = new StringBuilder();
        for(int carte : cartesEnMain){
            if(carte != -1){
                if(carte<10)
                    s.append("0");
                s.append(carte + " ");
            }
        }
        return s.toString();
    }

    public int[] getMain(){
        return cartesEnMain.clone();
    }
}
