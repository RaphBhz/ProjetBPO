package cartes;

import java.util.Stack;

public class Base {
    private int carteAscendante = 1;
    private int carteDescendante = 60;

    public int getTopPileAsc(){
        return this.carteAscendante;
    }

    public int getTopPileDesc(){
        return this.carteDescendante;
    }

    public void addCartePileDesc(int carte){this.carteDescendante = carte;}

    public void addCartePileAsc(int carte){this.carteAscendante = carte;}

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
