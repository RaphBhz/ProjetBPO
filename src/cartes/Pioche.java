package cartes;

import java.util.Collections;
import java.util.Stack;

public class Pioche {
    private static final int MAX_PIOCHE = 58;
    private Stack<Integer> cartesPioche = new Stack<Integer>();
    private static final int MAX_MAIN = 6;
    private int[] cartesEnMain =  new int[MAX_MAIN];


    public Pioche(){
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

    public boolean mainVide(){
        for(int i = 0; i < MAX_MAIN; i++){
            if(cartesEnMain[i] != -1)
                return false;
        }
        return true;
    }

    public int pickCarte(){
        return cartesPioche.pop();
    }

    public boolean piocheVide(){
        return this.cartesPioche.empty();
    }

    public int nbCartes(){
        return cartesPioche.size();
    }
}
