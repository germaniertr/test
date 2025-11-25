package recette.domain;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
    public void testGetSet() {
        this.entiteRef.setNom(nomRef);
        this.entiteRef.setDetail(detailRef);

        Assertions.assertEquals(identifiantRef, entiteRef.getIdentifiant());
        Assertions.assertEquals(nomRef, entiteRef.getNom());
        Assertions.assertEquals(detailRef, entiteRef.getDetail());
    }
}
