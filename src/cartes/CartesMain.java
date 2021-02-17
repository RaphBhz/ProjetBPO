package cartes;

public class CartesMain {
    private static final int MAX_MAIN = 6;
    private int[] main;

    public CartesMain(){
        this.main = new int[MAX_MAIN];
    }

    public boolean mainVide(){
        for(int i = 0; i < MAX_MAIN; i++){
            if(this.main[i] != -1)
                return false;
        }
        return true;
    }
}
