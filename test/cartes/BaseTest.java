package cartes;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaseTest {

    @Test
    public void testGetTopPileAsc() {
        Base base = new Base();
        assertEquals(1,base.getTopPileAsc());
    }

    @Test
    public void testGetTopPileDesc() {
        Base base = new Base();
        assertEquals(60,base.getTopPileDesc());
    }

    @Test
    public void testAddCartePileDesc() {
        Base base = new Base();
        int newDesc = 50;
        base.addCartePileDesc(newDesc);

        assertEquals(newDesc, base.getTopPileDesc());

    }

    @Test
    public void testAddCartePileAsc() {
        Base base = new Base();
        int newAsc = 10;
        base.addCartePileAsc(newAsc);

        assertEquals(newAsc, base.getTopPileAsc());
    }

    @Test
    public void testToString() {
        Base base = new Base();

        assertEquals("^[01] v[60]",base.toString());


    }

    @Test
    public void testDeuxToString() {
        Base base = new Base();

        int newAsc = 10;
        base.addCartePileAsc(newAsc);

        assertNotEquals("^[01] v[60]", base.toString());


    }
}