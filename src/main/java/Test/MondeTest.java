package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MondeTest {

    protected Happyhours hp;
    // valeurs valides
    @BeforeEach
    void setUp() throws Exception {
        hp=new Happyhours();
    }
    @AfterEach
    public void tearDown() throws Exception {
        hp=null;
    }
    @Test
    void testReduc1() {
        assertEquals(0.05,hp.reduc(10), 0.01);
    }
    @Test
    void testReduc2() {
        assertEquals(0.1,hp.reduc(16), 0.01);
    }
    @Test
    void testReduc3() {
        assertEquals(0.05,hp.reduc(23), 0.01);
    }
    // valeurs invalides
    @Test
    void testReduc4() {
        assertThrows(Exception.class, ()-> {hp.reduc(-1);});
    }
    @Test
    void testReduc5() {
        assertThrows(Exception.class, ()-> {hp.reduc(26);});
    }
}

}
