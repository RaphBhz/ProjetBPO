package cartes;

public class Base {
    private static final int MAX_BASE = 2;
    private int[] base;


    public Base(){
        this.base = new int[MAX_BASE];
        this.base[0] = 1;
        this.base[1] = 60;
    }
}
