package recette.domain;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class UniteBaseTest {

    private IdentifiantBase identifiantRef;
    private String codeRef;
    private UniteBase entiteRef;

    public UniteBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = new IdentifiantBase(UUID.randomUUID().toString());
        codeRef = "code référence";
        entiteRef = new UniteBase(identifiantRef);
    }

    @Test
    public void testGetSet() {
        entiteRef.setCode(codeRef);
        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(codeRef, entiteRef.getCode());
    }

    @Test
    public void testSomeMethod() {
    }

}
