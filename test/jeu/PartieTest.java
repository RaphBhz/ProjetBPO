package jeu;

import org.junit.Test;

import java.util.Formatter;
import java.util.Random;

import static org.junit.Assert.*;

public class PartieTest {

    @Test
    public void testTraiterInput() {
        Partie game = new Partie();
        assertTrue(game.traiterInput("02^") && game.traiterInput("62^ 61^") && game.traiterInput("57^ 56^") &&
                game.traiterInput("56v 57v") && game.traiterInput("") && game.traiterInput("57v' 56v'"));

    }

    @Test
    public void testStringDeCartesAddedEtRemoved() {
        Partie game = new Partie();
        String s = game.StringDeCartesAddedEtRemoved();
        assertEquals("-1 cartes posées, -1 cartes piochées", s);
    }

    @Test
    public void testDeuxStringDeCartesAddedEtRemoved() {
        Partie game = new Partie();
        Random rd = new Random();
        int rdInt;
        while (true) {
            rdInt = rd.nextInt(58) + 2;
            if (!game.traiterInput(rdInt + "v " + rd.nextInt(rdInt) + "v"))
                break;
        }
        assertEquals("2 cartes posées, 2 cartes piochées", game.StringDeCartesAddedEtRemoved());
    }

    @Test
    public void testGagnantString() {
        Partie game = new Partie();
        String s = game.gagnantString();
        assertEquals("partie finie, SUD a gagné", s);
    }

    @Test
    public void testContinuer() {
        Partie game = new Partie();
        assertTrue(game.continuer());
    }

    @Test
    public void testAffiche() {
        Partie game = new Partie();
        String s = game.affiche();
        assertTrue(s.matches("NORD \\^\\[01\\] v\\[60\\] \\(m6p52\\)\\nSUD  \\^\\[01\\] v\\[60\\] \\(m6p52\\)\\ncartes NORD [{] (([1-5][0-9] )|(0[2-9] )){6}[}]"));
    }

    @Test
    public void testDeuxAffiche() {
        Partie game = new Partie();
        Formatter formatter = new Formatter();

        Random rd = new Random();
        int rdInt, SdRdInt;
        while (true) {
            rdInt = rd.nextInt(58) + 2;
            SdRdInt = rd.nextInt(rdInt);
            if (!game.traiterInput(rdInt + "v " + SdRdInt + "v"))
                break;
        }

        String s = game.affiche();
        assertTrue(s.matches("NORD \\^\\[01\\] v\\["+ String.format("%02d", SdRdInt)+"\\] \\(m6p50\\)\\nSUD  \\^\\[01\\] v\\[60\\] \\(m6p52\\)\\ncartes SUD [{] (([1-5][0-9] )|(0[2-9] )){6}[}]"));
    }
}