package cartes;

import java.util.Collections;
import java.util.Stack;

public class Pioche {
    private static final int MAX_PIOCHE = 60;
    private Stack<Integer> pioche;


    public Pioche(){
        this.pioche = new Stack<Integer>();

        for(int i = 2; i < MAX_PIOCHE; i++){
            this.pioche.push(i);
        }
        Collections.shuffle(this.pioche);
    }

    public boolean piocheVide(){
        return this.pioche.empty();
    }
}
