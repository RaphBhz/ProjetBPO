package cartes;

public class Main {
    public static final int MAX_MAIN = 6;
    public Carte[] cartesMain;

    public Main(){
        this.cartesMain = new Carte[MAX_MAIN];
        for(int i = 0; i<MAX_MAIN; i++){
            this.cartesMain[i] = new Carte();
        }
    }

    public void changer(Carte carte, int pos){
            this.cartesMain[pos] = carte;
    }

    public boolean mainVide(){
        for(int i = 0; i < MAX_MAIN; i++){
            if(this.cartesMain[i].valeur != -1)
                return false;
        }
        return true;
    }

    public int nbCartes(){
        int compteur = 0;
        for(int i = 0; i<MAX_MAIN; i++){
            if(this.cartesMain[i].valeur == -1)
                compteur++;
        }
        return MAX_MAIN - compteur;
    }

    public void initialiser(Pioche pioche){
        for(int i = 0; i < MAX_MAIN; i++){
            this.cartesMain[i] = pioche.cartesPioche.pop();
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("{ ");
        for(int i = 0; i<MAX_MAIN; i++){
            s.append(this.cartesMain[i]);
            s.append(" ");
        }
        s.append("}");

        return s.toString();
    }
}
