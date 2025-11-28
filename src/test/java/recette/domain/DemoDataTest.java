package recette.domain;

import core.domain.Identifiant;
import core.domain.IdentifiantBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dominique huguenin (dominique.huguenin AT rpn.ch)
 */
public class DemoDataTest {

    private final DemoData demoData;

    public DemoDataTest() {
        demoData = new DemoData();
    }

    @BeforeEach
    public void setUp() {
        demoData.initialisation();
    }

    @Test
    public void testGetUnite() {
        Identifiant id = IdentifiantBase.builder()
                .uuid(DemoData.UNITES.C_C.UUID)
                .build();
        Unite entite = demoData.getUnites()
                .get(id);

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(id, entite.getIdentifiant());

    }

    @Test
    public void testGetSameUnite() {
        Identifiant id = IdentifiantBase.builder()
                .uuid(DemoData.UNITES.C_C.UUID)
                .build();
        Unite entite1 = demoData.getUnites()
                .get(id);
        Unite entite2 = demoData.getUnites()
                .get(id);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertSame(entite1, entite2);

    }

    @Test
    public void testGetRecette() {
        Identifiant id = IdentifiantBase.builder()
                .uuid(DemoData.RECETTES.AUBERGINES_AU_FOUR.UUID)
                .build();
        Recette entite = demoData.getRecettes()
                .get(id);

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(id, entite.getIdentifiant());

    }

    @Test
    public void testGetRecetteSame() {
        Identifiant id = IdentifiantBase.builder()
                .uuid(DemoData.RECETTES.AUBERGINES_AU_FOUR.UUID)
                .build();
        Recette entite1 = demoData.getRecettes()
                .get(id);
        Recette entite2 = demoData.getRecettes()
                .get(id);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertSame(entite1, entite2);

    }

    @Test
    public void testGetIngredient() {
        Identifiant id = IdentifiantBase.builder()
                .uuid(DemoData.INGREDIENTS.AUBERGINE.UUID)
                .build();
        Ingredient entite = demoData.getIngredients()
                .get(id);

        Assertions.assertNotNull(entite);
        Assertions.assertEquals(id, entite.getIdentifiant());

    }

    @Test
    public void testGetSameIngredient() {
        Identifiant id = IdentifiantBase.builder()
                .uuid(DemoData.UNITES.C_C.UUID)
                .build();
        Ingredient entite1 = demoData.getIngredients()
                .get(id);
        Ingredient entite2 = demoData.getIngredients()
                .get(id);

        Assertions.assertEquals(entite1, entite2);
        Assertions.assertSame(entite1, entite2);

    }

}
