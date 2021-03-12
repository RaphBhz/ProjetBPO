package jeu;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JoueurTest {

    @Test
    public void testAjouterCarteBase() {
        Joueur j = new Joueur();
        int carte = 10;
        j.ajouterCarteBase(carte, true);

        assertEquals(carte, j.getTopPileAsc());
    }

    @Test
    public void testIsCartePosable() {
        Joueur j = new Joueur();
        int carte = 2;

        assertTrue(j.isCartePosable(carte, true));

    }

    @Test
    public void testDeuxIsCartePosable() {
        Joueur j = new Joueur();
        int carte = 2;
        j.ajouterCarteBase(10, true);

        assertFalse(j.isCartePosable(carte, true));

    }

    @Test
    public void testTroisIsCartePosable() {
        Joueur j = new Joueur();
        int carte = 29;
        j.ajouterCarteBase(carte, true);


        assertTrue(j.isCartePosable(carte-10, true));

    }

    @Test
    public void TestIsCartePosableEnnemi() {
        Joueur j = new Joueur();
        Joueur j2 = new Joueur();

        assertFalse(j.isCartePosableEnnemi(5,true));
    }

    @Test
    public void TestDeuxIsCartePosableEnnemi() {
        Joueur j = new Joueur();
        Joueur j2 = new Joueur();

        j.ajouterCarteBase(10, true);

        assertTrue(j.isCartePosableEnnemi(5,true));
    }

    @Test
    public void TestTroisIsCartePosableEnnemi() {
        Joueur j = new Joueur();
        Joueur j2 = new Joueur();

        j.ajouterCarteBase(10, true);

        assertFalse(j.isCartePosableEnnemi(20,true));
    }

    @Test
    public void testPeutJouer() {
        Joueur j = new Joueur();

        assertTrue(j.peutJouer(1, 60));
    }

    @Test
    public void testToString() {
        Joueur j = new Joueur(), j2 = new Joueur();
        boolean jNord = j.toString().equals("^[01] v[60] (m6p52)"), jSud = j2.toString().equals("^[01] v[60] (m6p52)");
        assertTrue(jNord && jSud);
    }

    @Test
    public void testAfficheMain() {
        Joueur j = new Joueur();
        assertTrue(j.afficheMain().matches("[{] (([1-5][0-9] )|(0[2-9] )){6}[}]"));
    }

    @Test
    public void TestToStringNbCartesAddedAndRemoved() {
        Joueur j = new Joueur();
        assertEquals("-1 cartes posées, -1 cartes piochées",j.toStringNbCartesAddedAndRemoved());
    }

    @Test
    public void TestAreCartesInMain() {
        Joueur j = new Joueur();
        ArrayList<Integer> tabCarteAsc = new ArrayList<>();
        ArrayList<Integer> tabCarteDesc = new ArrayList<>();
        tabCarteAsc.add(60);
        tabCarteDesc.add(1);

        assertFalse(j.areCartesInMain(tabCarteAsc,tabCarteDesc,-1));
    }

    @Test
    public void testGetTopPileAsc() {
        Joueur j = new Joueur();

        assertEquals(1, j.getTopPileAsc());
    }

    @Test
    public void testGetTopPileDesc() {
        Joueur j = new Joueur();

        assertEquals(60, j.getTopPileDesc());
    }

    @Test
    public void testRemoveCartesAndAddCartes() {
        Joueur j = new Joueur();

        ArrayList<Integer> tabCarteAsc = new ArrayList<>();
        ArrayList<Integer> tabCarteDesc = new ArrayList<>();
        tabCarteAsc.add(60);
        tabCarteDesc.add(1);

        j.removeCartesAndAddCartes(tabCarteAsc,tabCarteDesc,-1);

        assertEquals("0 cartes posées, 0 cartes piochées",j.toStringNbCartesAddedAndRemoved());
    }
}