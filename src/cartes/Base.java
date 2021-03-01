package cartes;

import java.util.Stack;

public class Base {
    private static final int MAX_BASE = 2;
    private Stack<Integer> pileAscendante = new Stack<Integer>();
    private Stack<Integer> pileDescendante = new Stack<Integer>();

    public Base(){
        this.pileAscendante.push(1);
        this.pileDescendante.push(60);
    }

    public int getTopPileAsc(){
        return pileAscendante.peek();
    }

    public int getTopPileDesc(){
        return pileDescendante.peek();
    }

    public void addCartePileDesc(int carte){
        pileDescendante.push(carte);
    }

    public void addCartePileAsc(int carte){
        pileAscendante.push(carte);
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("^[");
        //s.append(this.carteAscendante.toString());
        s.append("] v[");
        //s.append(this.carteDescendante.toString());
        s.append("]");
        return s.toString();
    }
}
