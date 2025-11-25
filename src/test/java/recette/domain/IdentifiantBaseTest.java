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

}
