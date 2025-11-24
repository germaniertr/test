package recette.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IdentifiantBaseTest {

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

}
