package recette.domain;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class IngredientBaseTest {

    private IdentifiantBase identifiantRef;
    private String nomRef;
    private String detailRef;
    private IngredientBase entiteRef;

    public IngredientBaseTest() {
    }

    @BeforeEach
    public void setUp() {
        identifiantRef = new IdentifiantBase(UUID.randomUUID().toString());
        nomRef = "nom de référence";
        detailRef = "détail de référence";

        entiteRef = new IngredientBase(identifiantRef);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetIdentifiant() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testGetNom() {
    }

    @Test
    public void testSetNom() {
    }

    @Test
    public void testGetDetail() {
    }

    @Test
    public void testSetDetail() {
    }

    @Test
    public void testGetRecette() {
    }

    @Test
    public void testSetRecette() {
    }

}
