package cartes;

public class Base {
    private static final int MAX_BASE = 2;
    private Carte carteAscendante;
    private Carte carteDescendante;

    public Base(){
        this.carteAscendante = new Carte();
        this.carteDescendante = new Carte();
        this.carteAscendante.valeur = 1;
        this.carteDescendante.valeur = 60;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("^[");
        s.append(this.carteAscendante.toString());
        s.append("] v[");
        s.append(this.carteDescendante.toString());
        s.append("]");
        return s.toString();
    }
}
