package cartes;

public class Carte {
    public int valeur;

    public Carte(){
        this.valeur = -1;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        if(this.valeur<10)
            s.append('0');
        s.append(this.valeur);
        return s.toString();
    }
}
