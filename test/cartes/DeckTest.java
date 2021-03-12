package cartes;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void testIsCarteInMain() {

        Deck deck = new Deck();

        assertTrue(deck.isCarteInMain(deck.getMain()[1]));
    }

    @Test
    public void testDeuxIsCarteInMain() {

        Deck deck = new Deck();

        assertFalse(deck.isCarteInMain(deck.getMain()[1]+1));

    }

    @Test
    public void removeCartes() {
        Deck deck = new Deck();
        ArrayList<Integer> tabCarteAsc = new ArrayList<>();
        ArrayList<Integer> tabCarteDesc = new ArrayList<>();
        tabCarteAsc.add(deck.getMain()[0]);
        tabCarteDesc.add(deck.getMain()[4]);

        deck.removeCartes(tabCarteAsc, tabCarteDesc, -1);

        assertTrue(deck.getMain()[0] == -1 && deck.getMain()[4] == -1);
    }

    @Test
    public void testAddCartes() {

        Deck deck = new Deck();

        assertEquals(0, deck.addCartes(1));
    }

    @Test
    public void testDeuxAddCartes() {

        Deck deck = new Deck();

        ArrayList<Integer> tabCarteAsc = new ArrayList<>();
        ArrayList<Integer> tabCarteDesc = new ArrayList<>();
        tabCarteAsc.add(deck.getMain()[0]);
        tabCarteDesc.add(deck.getMain()[4]);
        tabCarteDesc.add(deck.getMain()[3]);

        deck.removeCartes(tabCarteAsc, tabCarteDesc, -1);

        deck.addCartes(-1);

        assertEquals(5, deck.nbCartesMain());
    }

    @Test
    public void testPiocheVide() {
        Deck deck = new Deck();
        assertFalse(deck.piocheVide());
    }

    @Test
    public void oneCarteInHandAndZeroInPioche() {
        Deck deck = new Deck();
        assertFalse(deck.oneCarteInHandAndZeroInPioche());
    }

    @Test
    public void testNbCartes() {
        Deck deck = new Deck();
        assertEquals(52,deck.nbCartes());
    }

    @Test
    public void testNbCartesMain() {
        Deck deck = new Deck();
        assertEquals(6,deck.nbCartesMain());
    }

    @Test
    public void testAfficheMain() {
        Deck deck = new Deck();
        boolean afficheAndMainEgaux = true;
        String s = deck.afficheMain();
        String[] tab = s.split("\\s+");
        int[] tabMain = deck.getMain();
        for (int i = 0; i<tab.length; i++){
            if (!(Integer.parseInt(tab[i]) == tabMain[i])){
                afficheAndMainEgaux = false;
                break;
            }

        }

        assertTrue(afficheAndMainEgaux);
    }

    @Test
    public void testGetMain() {
        Deck deck = new Deck();
        int[] tabMain = deck.getMain();
        boolean mainBonne = true;

        for (int carte : tabMain){
            if (carte < 2 || carte > 59){
                mainBonne = false;
                break;
            }
        }

        assertTrue(mainBonne);

    }
}