package recette.domain;

import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IdentifiantBaseTest {

    private static final Logger LOG = Logger.getLogger(IdentifiantBaseTest.class.getName());

    private String uuidRef;
    private IdentifiantBase idRef;

    public IdentifiantBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        uuidRef = "12345678-1234-1234-123456789012";
        idRef = new IdentifiantBase(uuidRef);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetUUID() {
        Assertions.assertEquals(uuidRef, idRef.getUUID());
    }

    @Test
    public void testToString() {
        //Ceci n'est pas un test!
        LOG.info(this.idRef.toString());
    }

    @Test
    public void testEquals() {
        Identifiant id2 = new IdentifiantBase(uuidRef);

        //Vérifier si idRef et id2 sont des instances différentes
        Assertions.assertTrue(idRef != id2);
        Assertions.assertNotSame(idRef, id2);

        //Vérifier si idRef.equals(id2) et id2.equals(idRef) sont vrai
        Assertions.assertEquals(idRef, id2);
        Assertions.assertTrue(idRef.equals(id2));
        Assertions.assertTrue(id2.equals(idRef));

        //Si idRef.equals(id2) alors les hashcodes doivent éguales
        Assertions.assertEquals(idRef.hashCode(), id2.hashCode());

    }
    
    @Test
    public void testEqualsSame() {
        Assertions.assertSame(this.idRef, this.idRef);
        Assertions.assertEquals(this.idRef, this.idRef);
        Assertions.assertEquals(this.idRef.hashCode(), idRef.hashCode());
    }

    @Test
    public void testEqualsNull() {
        Identifiant id = null;

        Assertions.assertNotSame(this.idRef, id);
        Assertions.assertNotEquals(this.idRef, id);
    }

    @Test
    public void testEqualsObject() {
        Object id = new Object();

        Assertions.assertNotSame(this.idRef, id);
        Assertions.assertNotEquals(this.idRef, id);
        Assertions.assertNotEquals(this.idRef.hashCode(), id.hashCode());
    }
    
}
