package recette.domain;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
    }

}
