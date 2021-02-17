package cartes;

import java.util.Collections;
import java.util.Stack;

public class Pioche {
    private static final int MAX_PIOCHE = 60;
    public Stack<Carte> cartesPioche;


    public Pioche(){
        this.cartesPioche = new Stack<>();


        for(int i = 2; i < MAX_PIOCHE; i++){
            Carte carte = new Carte();
            carte.valeur = i;
            this.cartesPioche.push(carte);
        }
        Collections.shuffle(this.cartesPioche);
    }

    public boolean piocheVide(){
        return this.cartesPioche.empty();
    }

    public int nbCartes(){
        return cartesPioche.size();
    }
}
